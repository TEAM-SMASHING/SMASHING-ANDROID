package com.smashing.app.data.model.event

import com.smashing.app.core.common.type.event.MatchingStatus
import com.smashing.app.core.common.type.event.NotificationType

sealed interface SseEvent {

    data object SystemConnected : SseEvent

    data class MatchingReceived(
        val matchingId: String,
    ) : SseEvent

    data class MatchingUpdated(
        val matchingId: String,
        val status: MatchingStatus,
    ) : SseEvent

    data class NotificationCreated(
        val notificationId: String,
        val notificationType: NotificationType,
        val targetId: String?,
    ) : SseEvent
}
