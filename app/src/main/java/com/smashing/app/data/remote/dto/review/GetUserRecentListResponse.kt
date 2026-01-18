package com.smashing.app.data.remote.dto.review

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class GetUserRecentListResponse(
    @SerialName("gameReviewId")
    val gameReviewId: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @Contextual
    @SerialName("createdAt")
    val createdAt: LocalDateTime,
    @SerialName("content")
    val content: String?,
)
