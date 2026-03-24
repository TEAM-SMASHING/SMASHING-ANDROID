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
    @SerialName("latestSubmissionId")
    val latestSubmissionId: String? = null,
    @SerialName("latestAttemptNo")
    val latestAttemptNo: Int? = null,
    @SerialName("latestSubmitterProfileId")
    val latestSubmitterProfileId: String? = null,
) {
    @Serializable
    data class OpponentSummary(
        @SerialName("opponentProfileId")
        val profileId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("tierCode")
        val tierCode: String,
    )
}
