package com.smashing.app.core.network.sse

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.data.repository.api.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SseManager @Inject constructor(
    private val eventRepository: EventRepository,
    private val authManager: AuthManager,
    @ApplicationScope private val scope: CoroutineScope,
) {
    private var shouldMaintainConnection = false
    private var isAppInForeground = false
    private val mutex = Mutex()

    init {
        scope.launch {
            authManager.isUserLoggedIn.collectLatest { isLoggedIn ->
                mutex.withLock {
                    shouldMaintainConnection = isLoggedIn
                    if (!isLoggedIn) {
                        Timber.tag(TAG).d("Auth state changed to logged out - disconnecting SSE")
                        eventRepository.disconnect()
                    } else if (isAppInForeground) {
                        Timber.tag(TAG).d("Auth state changed to logged in - connecting SSE")
                        eventRepository.connect()
                    }
                }
            }
        }
    }

    fun connect() {
        scope.launch {
            mutex.withLock {
                isAppInForeground = true
                if (!shouldMaintainConnection) {
                    Timber.tag(TAG).d("Connect - user not logged in, skipping")
                    return@withLock
                }

                Timber.tag(TAG).d("Connect - starting SSE")
                eventRepository.connect()
            }
        }
    }

    fun disconnect() {
        scope.launch {
            mutex.withLock {
                isAppInForeground = false
                Timber.tag(TAG).d("Disconnect - stopping SSE")
                eventRepository.disconnect()
            }
        }
    }

    companion object {
        private const val TAG = "SSE LOG"
    }
}
