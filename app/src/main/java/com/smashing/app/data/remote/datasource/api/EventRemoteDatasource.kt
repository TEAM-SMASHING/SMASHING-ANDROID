package com.smashing.app.data.remote.datasource.api

import com.smashing.app.core.network.sse.SseConnectionState
import com.smashing.app.data.remote.dto.event.RawEventResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EventRemoteDataSource {
    val rawEvents: Flow<RawEventResponse>
    val connectionState: StateFlow<SseConnectionState>

    fun connect()
    fun disconnect()
}
