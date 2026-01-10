package com.smashing.app.core.common.type

enum class SportType(
    val id: Long,
    val sportName: String,
    val code: String,
) {
    PING_PONG(
        id = 1,
        sportName = "탁구",
        code = "TT",
    ),
    TENNIS(
        id = 2,
        sportName = "테니스",
        code = "TN",
    ),
    BADMINTON(
        id = 3,
        sportName = "배드민턴",
        code = "BM",
    );

    companion object {
        fun findSportType(sportId: Long): SportType? = entries.find { it.id == sportId }
    }
}
