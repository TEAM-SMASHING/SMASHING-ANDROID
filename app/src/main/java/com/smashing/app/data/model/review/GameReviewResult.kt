package com.smashing.app.data.model.review

data class GameReviewResult(
    val bestCount: Long = 1,
    val goodCount: Long = 1,
    val badCount: Long = 1,
    val goodMannerCount: Long = 1,
    val onTimeCount: Long = 1,
    val fairPlayCount: Long = 1,
    val fastResponseCount: Long = 1,
)
