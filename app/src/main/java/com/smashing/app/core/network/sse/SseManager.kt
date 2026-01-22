package com.smashing.app.core.network.sse

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.repository.api.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SseManager @Inject constructor(
    private val eventRepository: EventRepository,
    private val tokenDataSource: LocalTokenDataSource,
    @ApplicationScope private val scope: CoroutineScope,
) {
    private var connectionJob: Job? = null
    private val mutex = Mutex()

    fun start() {
        scope.launch {
            mutex.withLock {
                if (connectionJob != null) return@launch

                connectionJob = scope.launch {
                    tokenDataSource.accessTokenFlow.collect { token ->
                        if (token != null) {
                            eventRepository.connect()
                        } else {
                            eventRepository.disconnect()
                        }
                    }
                }
            }
        }
    }

    fun stop() {
        scope.launch {
            mutex.withLock {
                connectionJob?.cancel()
                connectionJob = null
                eventRepository.disconnect()
            }
        }
    }
}
