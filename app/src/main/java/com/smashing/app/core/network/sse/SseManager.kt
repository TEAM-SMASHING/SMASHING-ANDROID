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

    fun connect() {
        scope.launch {
            mutex.withLock {
                if (isConnected) {
                    Timber.tag(TAG).d("connect - already connected")
                    return@launch
                }

                Timber.tag(TAG).d("connect - initiating SSE connection")
                eventRepository.connect()
                isConnected = true
            }
        }
    }

    fun disconnect() {
        scope.launch {
            mutex.withLock {
                if (!isConnected) {
                    Timber.tag(TAG).d("disconnect - not connected")
                    return@launch
                }

                Timber.tag(TAG).d("disconnect - terminating SSE connection")
                eventRepository.disconnect()
                isConnected = false
            }
        }
    }

    companion object {
        private const val TAG = "SSE LOG"
    }
}
