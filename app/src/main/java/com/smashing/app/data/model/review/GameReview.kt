package com.smashing.app.data.model.review

import java.time.LocalDateTime

data class GameReview(
    val gameReviewId: String,
    val opponentNickname: String,
    val createdAt: LocalDateTime,
    val content: String?,
)
