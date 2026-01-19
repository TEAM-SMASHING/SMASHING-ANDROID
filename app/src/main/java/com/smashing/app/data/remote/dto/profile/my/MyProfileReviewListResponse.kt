package com.smashing.app.data.remote.dto.profile.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MyProfileReviewListData(
    @SerialName("snapshotAt")
    val snapshotAt: String,
    @SerialName("nextCursor")
    val nextCursor: String,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("results")
    val results: List<MyReviewsDto>


)
@Serializable
data class MyReviewsDto(
    @SerialName("gameReviewId")
    val gameReviewId: String,
    @SerialName("opponentNickname")
    val nickname: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("content")
    val content: String?,
)
