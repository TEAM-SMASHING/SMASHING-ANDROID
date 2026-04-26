package com.smashing.app.core.network.sse

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.core.network.sse.SseManager.Companion.MAX_RECONNECT_DELAY_MS
import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.data.repository.api.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min
import kotlin.math.pow
import kotlin.random.Random

/**
 * SSE 연결 생명주기 관리 Manager
 *
 * - 세션 상태와 앱 포그라운드 상태를 기준으로 연결 상태를 동기화
 * - 연결 실패/해제 시 backoff + jitter 기반 재연결을 시도
 */
@Singleton
class SseManager @Inject constructor(
    private val eventRepository: EventRepository,
    private val authManager: AuthManager,
    @param:ApplicationScope private val scope: CoroutineScope,
) {
    private var shouldMaintainConnection = false
    private var isAppInForeground = false
    private var reconnectAttempt = 0
    private var reconnectJob: Job? = null
    private val mutex = Mutex()

    init {
        observeAuthState()
        observeConnectionState()
    }

    /**
     * 세션 상태를 구독하는 함수
     *
     * - 로그인/로그아웃 상태를 반영하고 연결 상태를 동기화
     */
    private fun observeAuthState() {
        scope.launch {
            authManager.isUserLoggedIn.collect { isLoggedIn ->
                mutex.withLock {
                    shouldMaintainConnection = isLoggedIn
                    updateConnectionLocked()
                }
            }
        }
    }

    /**
     * SSE 연결 상태 구독 함수
     *
     * - 연결 실패/해제 상태를 기반으로 재연결 스케줄링
     */
    private fun observeConnectionState() {
        scope.launch {
            eventRepository.connectionState.collect { state ->
                when (state) {
                    is SseConnectionState.Connected -> {
                        mutex.withLock { resetReconnectLocked() }
                    }

                    is SseConnectionState.Error, SseConnectionState.Disconnected -> {
                        mutex.withLock { scheduleReconnectLocked() }
                    }

                    is SseConnectionState.Retrying -> Unit // TODO 재연결 시도시 UI 상태 반영
                }
            }
        }
    }

    /**
     * 포그라운드 진입 처리 함수
     *
     * - 포그라운드 상태 반영
     * - 로그인 상태시 연결
     */
    fun onAppForegrounded() {
        scope.launch {
            mutex.withLock {
                isAppInForeground = true
                updateConnectionLocked()
            }
        }
    }

    /**
     * 백그라운드 진입 처리 함수
     *
     * - 백그라운드 상태 반영
     * - 연결 해제
     */
    fun onAppBackgrounded() {
        scope.launch {
            mutex.withLock {
                isAppInForeground = false
                updateConnectionLocked()
            }
        }
    }

    /**
     * SSE 연결 상태 동기화 함수
     *
     * - 로그인/Foreground 시 연결 시작
     * - 조건 미충족 시 연결 해제
     */
    private fun updateConnectionLocked() {
        if (!shouldMaintainConnection || !isAppInForeground) {
            Timber.tag(TAG).d("Connection - stop")
            resetReconnectLocked()
            eventRepository.disconnect()
            return
        }

        Timber.tag(TAG).d("Connection - start")
        cancelReconnectLocked()
        eventRepository.connect()
    }

    /**
     * 재연결 스케줄링 함수
     *
     * - 로그인 + 포그라운드 조건일 때만 동작
     * - exponential backoff + full jitter 적용
     */
    private fun scheduleReconnectLocked() {
        if (!shouldMaintainConnection || !isAppInForeground) return
        if (reconnectJob?.isActive == true) return
        if (reconnectAttempt >= MAX_RECONNECT_ATTEMPTS) {
            Timber.tag(TAG).e("Reconnect - max attempts reached: $reconnectAttempt")
            return
        }

        val delayMs = calculateReconnectDelayMs(reconnectAttempt)
        val nextAttempt = reconnectAttempt + 1

        Timber.tag(TAG).w("Reconnect - attempt: $nextAttempt, delayMs: $delayMs")

        reconnectJob = scope.launch {
            delay(delayMs)
            mutex.withLock {
                reconnectJob = null
                if (!shouldMaintainConnection || !isAppInForeground) {
                    return@withLock
                }

                reconnectAttempt = nextAttempt
                eventRepository.connect()
            }
        }
    }

    /**
     * 진행 중인 재연결 작업을 취소하는 함수
     */
    private fun cancelReconnectLocked() {
        reconnectJob?.cancel()
        reconnectJob = null
    }

    /**
     * 재연결 작업 취소 + 시도 횟수 초기화 함수
     */
    private fun resetReconnectLocked() {
        cancelReconnectLocked()
        reconnectAttempt = 0
    }

    /**
     * full jitter 백오프 딜레이 계산 함수
     *
     * - minDelay * 2^attempt 값을 maxDelay [MAX_RECONNECT_DELAY_MS]로 제한
     * - [0, capped] 범위에서 랜덤 지연
     */
    private fun calculateReconnectDelayMs(attempt: Int): Long {
        val exponentialDelay = MIN_RECONNECT_DELAY_MS * 2.0.pow(attempt.toDouble())
        val cappedDelay = min(MAX_RECONNECT_DELAY_MS.toDouble(), exponentialDelay).toLong()
        return Random.nextLong(0L, cappedDelay + 1L)
    }

    companion object {
        private const val TAG = "SSE LOG"
        private const val MIN_RECONNECT_DELAY_MS = 1_000L
        private const val MAX_RECONNECT_DELAY_MS = 120_000L
        private const val MAX_RECONNECT_ATTEMPTS = 30
    }
}
