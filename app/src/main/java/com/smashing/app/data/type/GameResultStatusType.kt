package com.smashing.app.data.type

enum class GameResultStatusType {
    PENDING_RESULT,
    WAITING_CONFIRMATION,
    RESULT_REJECTED,
    CANCELED,
    RESULT_CONFIRMED,
    UNKNOWN;

    companion object {
        fun findByResultStatus(status: String): GameResultStatusType =
            GameResultStatusType.entries.find { it.name == status } ?: UNKNOWN
    }
}
