package com.smashing.app.core.common.type.event

enum class SseEventType(
    val eventName: String,
) {
    SYSTEM_CONNECTED("system.connected"),
    MATCHING_RECEIVED("matching.received"),
    MATCHING_UPDATED("matching.updated"),
    NOTIFICATION_CREATED("notification.created"),

    MATCHING_REQUESTED("matching.request.notification.created"),

    MATCHING_ACCEPTED("matching.accept.notification.created");

    companion object {
        fun fromEventName(eventName: String?): SseEventType? {
            return entries.find { it.eventName == eventName }
        }
    }
}
