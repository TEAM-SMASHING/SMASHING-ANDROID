package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.event.RawEventResponse
import kotlinx.coroutines.flow.Flow

interface RemoteEventDataSource {
    val rawEvents: Flow<RawEventResponse>

    fun connect()
    fun disconnect()
}
