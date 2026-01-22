package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.BuildConfig.BASE_URL
import com.smashing.app.data.remote.datasource.api.RemoteEventDataSource
import com.smashing.app.data.remote.dto.event.RawEventResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteEventDataSourceImpl @Inject constructor(
    private val eventSourceFactory: EventSource.Factory,
) : RemoteEventDataSource {

    private val _rawEvents =
        MutableSharedFlow<RawEventResponse>(replay = 0, extraBufferCapacity = 64)
    override val rawEvents: Flow<RawEventResponse> = _rawEvents

    @Volatile
    private var eventSource: EventSource? = null

    override fun connect() {
        if (eventSource != null) return

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
                cleanup()
            }

            override fun onClosed(eventSource: EventSource) {
                cleanup()
            }
        }

        eventSource = eventSourceFactory.newEventSource(request, eventListener)
    }

    override fun disconnect() {
        eventSource?.cancel()
        cleanup()
    }

    private fun cleanup() {
        eventSource = null
    }

    companion object {
        private const val SSE_URL = "${BASE_URL}api/v1/sse/subscribe"
    }
}
