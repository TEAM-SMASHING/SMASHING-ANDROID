package com.smashing.app.data.remote.dto.event

import com.smashing.app.data.type.MatchingStatusType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchingUpdatedDto(
    @SerialName("type")
    val type: String,
    @SerialName("matchingId")
    val matchingId: String,
    @SerialName("status")
    val status: MatchingStatusType,
)
