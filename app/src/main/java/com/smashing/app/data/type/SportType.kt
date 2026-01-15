package com.smashing.app.data.type

import com.smashing.app.R.drawable.ic_badminton
import com.smashing.app.R.drawable.ic_pingpong
import com.smashing.app.R.drawable.ic_tennis

enum class SportType(
    val id: Long,
    val sportName: String,
    val code: String,
    val iconRes: Int,
) {
    PING_PONG(
        id = 1,
        sportName = "탁구",
        code = "TT",
        iconRes = ic_pingpong,
    ),
    TENNIS(
        id = 2,
        sportName = "테니스",
        code = "TN",
        iconRes = ic_tennis,
    ),
    BADMINTON(
        id = 3,
        sportName = "배드민턴",
        code = "BM",
        iconRes = ic_badminton,
    );

    companion object {
        fun findSportType(sportId: Long): SportType? = entries.find { it.id == sportId }
    }
}
