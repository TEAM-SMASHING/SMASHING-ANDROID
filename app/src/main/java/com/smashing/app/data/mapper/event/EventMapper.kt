package com.smashing.app.data.mapper.event

import com.smashing.app.core.common.type.event.MatchingStatus
import com.smashing.app.core.common.type.event.NotificationType
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.remote.dto.event.MatchingReceivedDto
import com.smashing.app.data.remote.dto.event.MatchingUpdatedDto
import com.smashing.app.data.remote.dto.event.NotificationCreatedDto


fun MatchingReceivedDto.toEvent(): SseEvent.MatchingReceived =
    SseEvent.MatchingReceived(
        matchingId = matchingId,
    )

fun MatchingUpdatedDto.toEvent(): SseEvent.MatchingUpdated =
    SseEvent.MatchingUpdated(
        matchingId = matchingId,
        status = MatchingStatus.valueOf(status),
    )

fun NotificationCreatedDto.toEvent(): SseEvent.NotificationCreated =
    SseEvent.NotificationCreated(
        notificationId = notificationId,
        notificationType = NotificationType.valueOf(notificationType),
        targetId = targetId,
    )
