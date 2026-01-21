package com.smashing.app.data.remote.dto.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostConfirmSubmissionResponse(
    @SerialName("reviewId")
    val reviewId: String,
)
