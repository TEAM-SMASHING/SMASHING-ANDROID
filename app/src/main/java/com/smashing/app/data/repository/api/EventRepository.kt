package com.smashing.app.data.repository.api

import com.smashing.app.data.model.event.SseEvent
import kotlinx.coroutines.flow.SharedFlow

interface EventRepository {
    val events: SharedFlow<SseEvent>

    fun connect()
    fun disconnect()
}
