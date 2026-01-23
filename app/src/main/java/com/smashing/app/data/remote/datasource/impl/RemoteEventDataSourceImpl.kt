package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.BuildConfig.BASE_URL
import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.data.remote.datasource.api.RemoteEventDataSource
import com.smashing.app.data.remote.dto.event.RawEventResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteEventDataSourceImpl @Inject constructor(
    private val eventSourceFactory: EventSource.Factory,
    @ApplicationScope private val scope: CoroutineScope,
) : RemoteEventDataSource {

    private val _rawEvents =
        MutableSharedFlow<RawEventResponse>(replay = 0, extraBufferCapacity = 64)
    override val rawEvents: Flow<RawEventResponse> = _rawEvents

    @Volatile
    private var eventSource: EventSource? = null
    private val lock = Any()
    private var reconnectJob: Job? = null
    private var retryAttempt = 0

    @Volatile
    private var isManuallyDisconnected = false

    override fun connect() {
        synchronized(lock) {
            isManuallyDisconnected = false
            retryAttempt = 0
            if (eventSource != null) return
            reconnectJob?.cancel()
            connectInternal()
        }
    }

    override fun disconnect() {
        synchronized(lock) {
            isManuallyDisconnected = true
            reconnectJob?.cancel()
            eventSource?.cancel()
            cleanup()
        }
    }

    private fun cleanup() {
        eventSource = null
    }

    private fun connectInternal() {
        val request = Request.Builder()
            .url(SSE_URL)
            .get()
            .build()

        val eventListener = object : EventSourceListener() {
            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String,
            ) {
                val eventName = type ?: return

                _rawEvents.tryEmit(
                    RawEventResponse(
                        eventName = eventName,
                        data = data,
                    )
                )
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                scheduleReconnect()
            }

            override fun onClosed(eventSource: EventSource) {
                scheduleReconnect()
            }
        }

        eventSource = eventSourceFactory.newEventSource(request, eventListener)
    }

    private fun scheduleReconnect() {
        synchronized(lock) {
            if (isManuallyDisconnected) {
                cleanup()
                return
            }
            if (reconnectJob?.isActive == true) return
            if (retryAttempt >= MAX_RETRY_COUNT) {
                cleanup()
                return
            }

            cleanup()
            retryAttempt += 1
            reconnectJob = scope.launch {
                synchronized(lock) {
                    if (isManuallyDisconnected || eventSource != null) return@launch
                    connectInternal()
                }
            }
        }
    }

    companion object {
        private const val SSE_URL = "${BASE_URL}api/v1/sse/subscribe"
        private const val MAX_RETRY_COUNT = 3
    }
}
