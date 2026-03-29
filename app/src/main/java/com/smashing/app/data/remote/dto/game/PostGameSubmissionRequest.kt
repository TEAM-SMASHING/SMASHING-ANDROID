package com.smashing.app.data.remote.dto.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostGameSubmissionRequest(
    @SerialName("winnerProfileId")
    val winnerProfileId: String,
    @SerialName("loserProfileId")
    val loserProfileId: String,
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
