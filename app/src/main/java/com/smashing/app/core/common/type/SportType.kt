package com.smashing.app.core.common.type

enum class SportType(
    val code: String,
    val sport: String,
) {
    PING_PONG("PP", "탁구"),
    TENNIS("TN", "테니스"),
    BADMINTON("BM", "배드민턴");

    companion object {
        fun findSportType(code: String): SportType? {
            return entries.find { it.code == code }
        }
    }
}
