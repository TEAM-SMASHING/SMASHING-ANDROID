package com.smashing.app.data.remote.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameUpdatedDto(
    @SerialName("type")
    val type: String,
    @SerialName("gameId")
    val gameId: String,
    @SerialName("submissionId")
    val submissionId: String?,
    @SerialName("submissionAttemptNo")
    val submissionAttemptNo: Int?,
    @SerialName("resultStatus")
    val resultStatus: String,
)
