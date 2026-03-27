package com.smashing.app.data.remote.dto.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostGameSubmissionResponse(
    @SerialName("submissionId")
    val submissionId: String?,
)
