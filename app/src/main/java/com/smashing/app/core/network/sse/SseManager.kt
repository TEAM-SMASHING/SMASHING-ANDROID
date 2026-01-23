package com.smashing.app.core.network.sse

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.data.repository.api.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private var shouldConnect = false
    private val mutex = Mutex()

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    fun onUserLoggedIn() {
        scope.launch {
            mutex.withLock {
                shouldConnect = true
                _isUserLoggedIn.value = true

                Timber.tag(TAG).d("User logged in - connecting SSE")
                eventRepository.connect()
            }
        }
    }

    fun onUserLoggedOut() {
        scope.launch {
            mutex.withLock {
                shouldConnect = false
                _isUserLoggedIn.value = false

                Timber.tag(TAG).d("User logged out - disconnecting SSE")
                eventRepository.disconnect()
            }
        }
    }

    fun connect() {
        scope.launch {
            mutex.withLock {
                if (!shouldConnect) {
                    Timber.tag(TAG).d("Connect - user not logged in, skipping")
                    return@launch
                }

                Timber.tag(TAG).d("Connect - starting SSE")
                eventRepository.connect()
            }
        }
    }

    fun disconnect() {
        scope.launch {
            mutex.withLock {
                Timber.tag(TAG).d("Disconnect - stopping SSE")
                eventRepository.disconnect()
            }
        }
    }

    companion object {
        private const val TAG = "SSE LOG"
    }
}
