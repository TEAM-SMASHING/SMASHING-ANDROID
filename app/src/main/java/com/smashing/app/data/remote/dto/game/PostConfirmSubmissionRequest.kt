package com.smashing.app.data.remote.dto.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostConfirmSubmissionRequest(
    @SerialName("review")
    val review: ReviewRequest,
)

@Serializable
data class ReviewRequest(
    @SerialName("rating")
    val rating: String,
    @SerialName("content")
    val content: String?,
    @SerialName("tags")
    val tags: List<String>?,
)
