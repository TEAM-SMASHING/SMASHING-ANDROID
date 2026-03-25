package com.smashing.app.data.model.game

data class GameSubmission(
    val winnerUserId: String,
    val loserUserId: String,
    val review: Review?,
) {
    data class Review(
        val rating: String,
        val content: String?,
        val tags: List<String>?,
    )
}
