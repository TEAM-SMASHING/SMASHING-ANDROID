package com.smashing.app.data.repository.impl

import com.smashing.app.core.common.di.ApplicationScope
import com.smashing.app.data.mapper.event.toEvent
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.remote.datasource.api.EventRemoteDataSource
import com.smashing.app.data.remote.dto.event.GameUpdatedDto
import com.smashing.app.data.remote.dto.event.MatchingReceivedDto
import com.smashing.app.data.remote.dto.event.MatchingUpdatedDto
import com.smashing.app.data.repository.api.EventRepository
import com.smashing.app.data.type.SseEventType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventRemoteDataSource,
    private val json: Json,
    @ApplicationScope private val externalScope: CoroutineScope,
) : EventRepository {

    private val _events = MutableSharedFlow<SseEvent>(
        replay = 0,
        extraBufferCapacity = 64,
    )
    override val events: SharedFlow<SseEvent> = _events

    init {
        eventDataSource.rawEvents
            .onEach { raw ->
                Timber.tag(SSE_LOG_TAG)
                    .d("SSE Raw Event - name: %s, data: %s", raw.eventName, raw.data)

                val eventType = SseEventType.fromEventName(raw.eventName) ?: run {
                    return@onEach
                }

                val event: SseEvent = runCatching {
                    when (eventType) {
                        SseEventType.SYSTEM_CONNECTED -> SseEvent.SystemConnected

                        SseEventType.MATCHING_RECEIVED ->
                            json.decodeFromString<MatchingReceivedDto>(raw.data).toEvent()

                        SseEventType.MATCHING_UPDATED ->
                            json.decodeFromString<MatchingUpdatedDto>(raw.data).toEvent()

                        SseEventType.GAME_UPDATED ->
                            json.decodeFromString<GameUpdatedDto>(raw.data).toEvent()
                    }
                }.onFailure { error ->
                    Timber.tag(SSE_LOG_TAG)
                        .e(error, "SSE Event parsing failed - type: %s", eventType)
                }.getOrNull() ?: return@onEach

                Timber.tag(SSE_LOG_TAG).d("event emitted - %s", event)
                _events.tryEmit(event)
            }
            .launchIn(externalScope)
    }

    override fun connect() = eventDataSource.connect()
    override fun disconnect() = eventDataSource.disconnect()

    companion object {
        private const val SSE_LOG_TAG = "SSE LOG"
    }
}
