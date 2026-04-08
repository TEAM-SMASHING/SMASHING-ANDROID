package com.smashing.app.data.model.game

data class GameSubmission(
    val winnerProfileId: String,
    val loserProfileId: String,
    val review: Review?,
) {
    data class Review(
        val rating: String,
        val content: String?,
        val tags: List<String>?,
    )
}
