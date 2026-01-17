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
        @SerialName("userId")
        val userId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("reviewCount")
        val reviewCount: Long,
        @SerialName("tierId")
        val tierId: Long,
        @SerialName("tierName")
        val tierName: String,
        @SerialName("wins")
        val winCount: Int,
        @SerialName("losses")
        val loseCount: Int,
    )
}
