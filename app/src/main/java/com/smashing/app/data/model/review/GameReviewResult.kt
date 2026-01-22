package com.smashing.app.data.model.review

data class GameReviewResult(
    val bestCount: Long = 0L,
    val goodCount: Long = 0L,
    val badCount: Long = 0L,
    val goodMannerCount: Long = 0L,
    val onTimeCount: Long = 0L,
    val fairPlayCount: Long = 0L,
    val fastResponseCount: Long = 0L,
) {
    val isStatsEmpty: Boolean
        get() = bestCount == 0L && goodCount == 0L && badCount == 0L && onTimeCount == 0L
}
