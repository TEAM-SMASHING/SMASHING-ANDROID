package com.smashing.app.presentation.write.confirm.type

enum class ConfirmDenyReason(val description: String) {
    WRONG_WINNER("승자가 잘못됐어요"),
    WRONG_SCORE("스코어가 잘못됐어요"),
    WRONG_WINNER_AND_SCORE("승자와 스코어가 모두 잘못됐어요"),
    NOT_STARTED_YET("아직 진행하지 않은 경기에요");
}
