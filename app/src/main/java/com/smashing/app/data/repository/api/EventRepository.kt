package com.smashing.app.data.repository.api

import com.smashing.app.core.network.sse.SseConnectionState
import com.smashing.app.data.model.event.SseEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface EventRepository {
    val events: SharedFlow<SseEvent>
    val connectionState: StateFlow<SseConnectionState>

    fun connect()
    fun disconnect()
}
