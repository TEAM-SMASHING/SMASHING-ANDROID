package com.smashing.app.data.remote.dto.matching

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceivedMatchingListResponse(
    @SerialName("matchingId")
    val matchingId: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("status")
    val status: String,
    @SerialName("requester")
    val requester: RequesterSummary,
) {
    @Serializable
    data class RequesterSummary(
        @SerialName("requesterProfileId")
        val profileId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("reviewCount")
        val reviewCount: Long,
        @SerialName("tierCode")
        val tierCode: String,
        @SerialName("wins")
        val winCount: Int,
        @SerialName("losses")
        val loseCount: Int,
    )
}
