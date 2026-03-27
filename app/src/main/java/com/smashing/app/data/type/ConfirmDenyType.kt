package com.smashing.app.data.type

enum class ConfirmDenyType(val description: String) {
    WINNER_MISMATCH("승자가 잘못됐어요"),
    GAME_NOT_PLAYED_YET("아직 진행하지 않은 경기에요");

    companion object {
        fun findByDescription(description: String): ConfirmDenyType? =
            entries.find { it.description == description }
    }
}
