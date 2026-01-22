package com.smashing.app.core.network.sse

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.data.repository.api.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SseManager @Inject constructor(
    private val eventRepository: EventRepository,
    @ApplicationScope private val scope: CoroutineScope,
) {
    private var isConnected = false
    private val mutex = Mutex()

    /**
     * SSE 연결 시작 (로그인 성공 후 호출)
     */
    fun connect() {
        scope.launch {
            mutex.withLock {
                if (isConnected) {
                    return@launch
                }

                eventRepository.connect()
                isConnected = true
            }
        }
    }

    /**
     * SSE 연결 해제 (로그아웃 또는 앱 종료 시 호출)
     */
    fun disconnect() {
        scope.launch {
            mutex.withLock {
                if (!isConnected) {
                    return@launch
                }

                eventRepository.disconnect()
                isConnected = false
            }
        }
    }

    companion object {
        private const val TAG = "SseManager"
    }
}
