package com.smashing.app.data.model.review

data class GameReviewResult(
    val bestCount: Long = 0,
    val goodCount: Long = 0,
    val badCount: Long = 0,
    val goodMannerCount: Long = 0,
    val onTimeCount: Long = 0,
    val fairPlayCount: Long = 0,
    val fastResponseCount: Long = 0,
){
    val isStatsEmpty: Boolean
    get() = bestCount == 0L && goodCount == 0L && badCount == 0L && onTimeCount == 0L
}
