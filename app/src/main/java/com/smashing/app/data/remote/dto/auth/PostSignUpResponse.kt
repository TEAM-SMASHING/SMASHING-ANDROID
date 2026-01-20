package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("nickname") // TODO 수정 예정
    val userNickname: String,
)
