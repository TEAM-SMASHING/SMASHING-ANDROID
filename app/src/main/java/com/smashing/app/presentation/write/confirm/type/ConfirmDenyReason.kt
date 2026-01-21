package com.smashing.app.presentation.write.confirm.type

enum class ConfirmDenyType(val description: String) {
    WIN_LOSE_REVERSED("승자가 잘못됐어요"),
    SCORE_MISMATCH("스코어가 잘못됐어요"),
    SCORE_AND_WIN_LOSE_MISMATCH("승자와 스코어가 모두 잘못됐어요"),
    GAME_NOT_PLAYED_YET("아직 진행하지 않은 경기에요");

    companion object {
        fun findByDescription(description: String): ConfirmDenyType? =
            entries.find { it.description == description }
    }
}
