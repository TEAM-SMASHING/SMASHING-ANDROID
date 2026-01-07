package com.smashing.app.data.remote.dto.matching

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceivedMatchingSummaryDto(
    @SerialName("matchingId")
    val matchingId: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("status")
    val status: String,
    @SerialName("requester")
    val requester: RequesterSummaryDto,
)
