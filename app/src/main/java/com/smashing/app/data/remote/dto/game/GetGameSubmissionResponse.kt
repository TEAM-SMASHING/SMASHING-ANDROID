package com.smashing.app.data.remote.dto.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGameSubmissionResponse(
    @SerialName("attemptNo")
    val attemptNo: Int,
    @SerialName("submitter")
    val submitter: SubmitterSummary,
    @SerialName("winner")
    val winner: UserSummary,
    @SerialName("loser")
    val loser: UserSummary,
) {
    @Serializable
    data class SubmitterSummary(
        @SerialName("userId")
        val userId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("profileId")
        val profileId: String,
    )

    @Serializable
    data class UserSummary(
        @SerialName("profileId")
        val profileId: String,
        @SerialName("nickname")
        val nickname: String,
    )
}
