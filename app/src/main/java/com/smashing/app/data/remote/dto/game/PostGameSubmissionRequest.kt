package com.smashing.app.data.remote.dto.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostGameSubmissionRequest(
    @SerialName("winnerUserId")
    val winnerUserId: String,
    @SerialName("loserUserId")
    val loserUserId: String,
    @SerialName("review")
    val review: Review?,
) {
    @Serializable
    data class Review(
        @SerialName("rating")
        val rating: String,
        @SerialName("content")
        val content: String?,
        @SerialName("tags")
        val tags: List<String>?,
    )
}
