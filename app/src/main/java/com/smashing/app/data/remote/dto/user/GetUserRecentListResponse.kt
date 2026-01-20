package com.smashing.app.data.remote.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserRecentListResponse(
    @SerialName("gameReviewId")
    val gameReviewId: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("content")
    val content: String?,
)
