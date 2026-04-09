package com.smashing.app.core.network.sse

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.data.repository.api.EventRepository
import com.smashing.app.domain.usecase.auth.TokenReissueUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
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
 * - 인증 오류(401) 발생 시 토큰 재발급 후 재연결을 시도
 */
@Singleton
class SseManager @Inject constructor(
    private val eventRepository: EventRepository,
    private val authManager: AuthManager,
    private val tokenReissueUseCase: TokenReissueUseCase,
    @param:ApplicationScope private val scope: CoroutineScope,
) {
    private var shouldMaintainConnection = false
    private var isAppInForeground = false
    private var isReissuingToken = false
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
     * - 로그아웃 시 재발급 진행 상태를 초기화하고 연결을 정리
     * - 로그인 상태에서 앱이 포그라운드면 연결을 시작
     */
    private fun observeAuthState() {
        scope.launch {
            authManager.isUserLoggedIn.collectLatest { isLoggedIn ->
                mutex.withLock {
                    shouldMaintainConnection = isLoggedIn
                    if (!isLoggedIn) {
                        isReissuingToken = false
                    }
                    updateConnectionLocked()
                }
            }
        }
    }

    /**
     * SSE 연결 상태 구독 함수
     *
     * - 인증 오류(401) 감지 시 재발급 플로우를 시작
     */
    private fun observeConnectionState() {
        scope.launch {
            eventRepository.connectionState.collectLatest { state ->
                when (state) {
                    is SseConnectionState.Connected -> {
                        mutex.withLock {
                            reconnectAttempt = 0
                            reconnectJob?.cancel()
                            reconnectJob = null
                        }
                    }

                    is SseConnectionState.Error -> {
                        if (state.statusCode == AUTH_FAILURE_CODE) {
                            handleSseAuthFailure()
                        } else {
                            mutex.withLock {
                                scheduleReconnectLocked()
                            }
                        }
                    }

                    is SseConnectionState.Disconnected -> {
                        mutex.withLock {
                            scheduleReconnectLocked()
                        }
                    }

                    is SseConnectionState.Retrying -> Unit
                }
            }
        }
    }

    /**
     * 인증 오류 재발급 처리 함수
     *
     * - 토큰 재발급 시도
     * - 성공 시 현재 조건(로그인/포그라운드) 재검사 후 재연결
     */
    private fun handleSseAuthFailure() {
        scope.launch {
            if (!tryStartReissue()) return@launch

            val reissueResult = tokenReissueUseCase()

            mutex.withLock {
                isReissuingToken = false

                if (reissueResult.isSuccess && shouldMaintainConnection && isAppInForeground) {
                    reconnectAttempt = 0
                    reconnectJob?.cancel()
                    reconnectJob = null
                    eventRepository.connect()
                }
            }
        }
    }

    /**
     * 재발급 시작 가능 여부 검사 함수
     *
     * - 진행 조건(로그인/포그라운드/중복 실행 여부) 검사
     * @return 재발급을 시작하면 `true`, 아니면 `false`
     */
    private suspend fun tryStartReissue(): Boolean = mutex.withLock {
        if (!shouldMaintainConnection || !isAppInForeground || isReissuingToken) {
            return@withLock false
        }

        isReissuingToken = true
        Timber.tag(TAG).w("Reissue - start")
        reconnectJob?.cancel()
        reconnectJob = null
        eventRepository.disconnect()
        true
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
            reconnectJob?.cancel()
            reconnectJob = null
            reconnectAttempt = 0
            eventRepository.disconnect()
            return
        }

        Timber.tag(TAG).d("Connection - start")
        reconnectJob?.cancel()
        reconnectJob = null
        eventRepository.connect()
    }

    /**
     * 재연결 스케줄링 함수
     *
     * - 인증 재발급 중이 아니고, 로그인 + 포그라운드 조건일 때만 동작
     * - exponential backoff + full jitter 적용
     */
    private fun scheduleReconnectLocked() {
        if (!shouldMaintainConnection || !isAppInForeground || isReissuingToken) return
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
                if (!shouldMaintainConnection || !isAppInForeground || isReissuingToken) {
                    return@withLock
                }

                reconnectAttempt = nextAttempt
                eventRepository.connect()
            }
        }
    }

    /**
     * full jitter 백오프 딜레이 계산 함수
     *
     * - minDelay * 2^attempt 값을 maxDelay [MAX_RECONNECT_DELAY_MS]로 제한
     * - [0, capped] 범위에서 랜덤 지연
     */
    private fun calculateReconnectDelayMs(attempt: Int): Long {
        val exponentialDelay = MIN_RECONNECT_DELAY_MS * 2.0.pow(attempt.toDouble())
        val cappedDelay = min(MAX_RECONNECT_DELAY_MS, exponentialDelay).toLong()
        return Random.nextLong(0L, cappedDelay + 1L)
    }

    companion object {
        private const val TAG = "SSE LOG"
        private const val AUTH_FAILURE_CODE = 401
        private const val MIN_RECONNECT_DELAY_MS = 1_000L
        private const val MAX_RECONNECT_DELAY_MS = 120_000.0
        private const val MAX_RECONNECT_ATTEMPTS = 30
    }
}
