package com.smashing.app.data.remote.dto.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class  GetReviewDetailResponse(
    @SerialName("rating")
    val rating: String,
    @SerialName("reviewerNickname")
    val reviewerNickname: String,
    @SerialName("revieweeNickname")
    val revieweeNickname: String,
    @SerialName("tag")
    val tag: List<String>,
    @SerialName("content")
    val content: String?,
)
