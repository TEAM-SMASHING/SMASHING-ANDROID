package com.smashing.app.data.repository.impl

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.core.common.type.event.SseEventType
import com.smashing.app.data.mapper.toEvent
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.remote.datasource.api.RemoteEventDataSource
import com.smashing.app.data.remote.dto.event.MatchingReceivedDto
import com.smashing.app.data.remote.dto.event.MatchingUpdatedDto
import com.smashing.app.data.remote.dto.event.NotificationCreatedDto
import com.smashing.app.data.repository.api.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteEventDataSource,
    private val json: Json,
    @ApplicationScope private val externalScope: CoroutineScope,
) : EventRepository {

    private val _events = MutableSharedFlow<SseEvent>(
        replay = 0,
        extraBufferCapacity = 64,
    )
    override val events: SharedFlow<SseEvent> = _events

    init {
        remoteDataSource.rawEvents
            .onEach { raw ->
                val eventType = SseEventType.fromEventName(raw.eventName) ?: return@onEach

                val event: SseEvent = runCatching {
                    when (eventType) {
                        SseEventType.SYSTEM_CONNECTED -> SseEvent.SystemConnected

                        SseEventType.MATCHING_RECEIVED ->
                            json.decodeFromString<MatchingReceivedDto>(raw.data).toEvent()

                        SseEventType.MATCHING_UPDATED ->
                            json.decodeFromString<MatchingUpdatedDto>(raw.data).toEvent()

                        SseEventType.NOTIFICATION_CREATED ->
                            json.decodeFromString<NotificationCreatedDto>(raw.data).toEvent()
                    }
                }.getOrNull() ?: return@onEach

                _events.tryEmit(event)
            }
            .launchIn(externalScope)
    }

    override fun connect() = remoteDataSource.connect()
    override fun disconnect() = remoteDataSource.disconnect()
}
