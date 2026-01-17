package com.smashing.app.data.model.game

data class GameSubmission(
    val winnerUserId: String,
    val loserUserId: String,
    val winnerScore: Int,
    val loserScore: Int,
    val review: Review?,
)
