package com.smashing.app.data.remote.dto.ranking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingListResponse(
    @SerialName("topUsers")
    val topUsers: List<TopUserResponse>,
    @SerialName("user")
    val user: UserResponse?,
) {
    @Serializable
    data class TopUserResponse(
        @SerialName("rank")
        val rank: Int,
        @SerialName("userId")
        val userId: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("tierCode")
        val tierCode: String,
        @SerialName("lp")
        val lp: Int,
    )

    @Serializable
    data class UserResponse(
        @SerialName("nickname")
        val nickname: String,
        @SerialName("tierCode")
        val tierCode: String,
        @SerialName("lp")
        val lp: Int,
    )
}