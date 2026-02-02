package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.BuildConfig.BASE_URL
import com.smashing.app.core.network.sse.SseConnectionState
import com.smashing.app.data.remote.datasource.api.EventRemoteDataSource
import com.smashing.app.data.remote.dto.event.RawEventResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRemoteDataSourceImpl @Inject constructor(
    private val eventSourceFactory: EventSource.Factory,
) : EventRemoteDataSource {

    private val _rawEvents = MutableSharedFlow<RawEventResponse>(
        replay = 0,
        extraBufferCapacity = 64
    )
    override val rawEvents: Flow<RawEventResponse> = _rawEvents

    private val _connectionState =
        MutableStateFlow<SseConnectionState>(SseConnectionState.Disconnected)
    val connectionState: StateFlow<SseConnectionState> = _connectionState.asStateFlow()

    @Volatile
    private var eventSource: EventSource? = null

    override fun connect() {
        if (eventSource != null) {
            return
        }

        val request = Request.Builder()
            .url(SSE_URL)
            .build()

        eventSource = eventSourceFactory.newEventSource(request, object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: Response) {
                _connectionState.value = SseConnectionState.Connected
            }

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                val eventName = type ?: return

                _rawEvents.tryEmit(
                    RawEventResponse(
                        eventName = eventName,
                        data = data,
                    )
                )
            }

            override fun onFailure(
                eventSource: EventSource,
                t: Throwable?,
                response: Response?
            ) {
                _connectionState.value = SseConnectionState.Error(t, 0)
                eventSource.cancel()
                this@EventRemoteDataSourceImpl.eventSource = null
            }

            override fun onClosed(eventSource: EventSource) {
                this@EventRemoteDataSourceImpl.eventSource = null
                _connectionState.value = SseConnectionState.Disconnected
            }
        })
    }

    override fun disconnect() {
        eventSource?.cancel()
        eventSource = null
        _connectionState.value = SseConnectionState.Disconnected
    }

    companion object {
        private const val SSE_URL = "${BASE_URL}api/v1/sse/subscribe"
    }
}
