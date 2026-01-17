package com.smashing.app.data.remote.dto.matching

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptedMatchingListResponse(
    @SerialName("gameId")
    val gameId: String,
    @SerialName("resultStatus")
    val resultStatus: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("opponent")
    val opponentSummary: OpponentSummary,
    @SerialName("submitAvailableAt")
    val submitAvailableAt: String,
    @SerialName("remainingSeconds")
    val remainingSeconds: Long,
    @SerialName("isSubmitLocked")
    val isSubmitLocked: Boolean,
) {
    @Serializable
    data class OpponentSummary(
        @SerialName("userId")
        val userId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("openchatUrl")
        val openChatUrl: String?,
        @SerialName("gender")
        val gender: String,
        @SerialName("tierCode")
        val tierCode: String,
    )
}
