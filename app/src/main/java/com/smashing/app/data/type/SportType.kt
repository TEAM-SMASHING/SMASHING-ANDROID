package com.smashing.app.data.type

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
        fun findSportTypeToSportName(sportName: String): SportType = entries.find { it.sportName == sportName } ?: PING_PONG
        fun findSportTypeToSportCode(sportCode: String): SportType = entries.find { it.code == sportCode } ?: PING_PONG
    }
}
