package com.smashing.app.data.type

enum class SseEventType(
    val eventName: String,
) {
    SYSTEM_CONNECTED("system.connected"),
    MATCHING_RECEIVED("matching.received"),
    MATCHING_UPDATED("matching.updated"),
    MATCHING_REQUEST_NOTIFICATION_CREATED("matching.request.notification.created"),
    MATCHING_ACCEPT_NOTIFICATION_CREATED("matching.accept.notification.created"),
    GAME_UPDATED("game.updated"),
    GAME_RESULT_SUBMITTED_NOTIFICATION_CREATED("game.result.submitted.notification.created"),
    GAME_RESULT_REJECTED_NOTIFICATION_CREATED("game.result.rejected.notification.created"),
    REVIEW_RECEIVED_NOTIFICATION_CREATED("review.received.notification.created");

    companion object {
        fun fromEventName(eventName: String?): SseEventType? {
            return entries.find { it.eventName == eventName }
        }
    }
}
