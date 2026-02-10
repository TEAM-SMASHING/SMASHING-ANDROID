package com.smashing.app.data.remote.dto.review


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetMyRecentReviewListResponse(
    @SerialName("gameReviewId")
    val gameReviewId: String,
    @SerialName("opponentNickname")
    val nickname: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("content")
    val content: String?,
)
