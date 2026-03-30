package com.smashing.app.data.type

enum class SseEventType(
    val eventName: String,
) {
    SYSTEM_CONNECTED("system.connected"),
    MATCHING_RECEIVED("matching.received"),
    MATCHING_UPDATED("matching.updated"),
    GAME_UPDATED("game.updated");

    companion object {
        fun fromEventName(eventName: String?): SseEventType? {
            return entries.find { it.eventName == eventName }
        }
    }
}
