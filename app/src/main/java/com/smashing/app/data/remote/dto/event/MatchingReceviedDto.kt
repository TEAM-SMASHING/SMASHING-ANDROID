package com.smashing.app.data.remote.dto.event

import com.smashing.app.data.remote.dto.event.common.RequesterDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchingReceivedDto(
    @SerialName("type")
    val type: String,
    @SerialName("matchingId")
    val matchingId: String,
    @SerialName("sportId")
    val sportId: Long,
    @SerialName("receiverProfileId")
    val receiverProfileId: String,
    @SerialName("requester")
    val requester: RequesterDto,
)
