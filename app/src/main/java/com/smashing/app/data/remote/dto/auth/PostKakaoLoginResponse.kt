package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginResponse(
    @SerialName("accessToken")
    val accessToken: String?,
    @SerialName("refreshToken")
    val refreshToken: String?,
    @SerialName("kakaoId")
    val kakaoId: String,
    @SerialName("userId")
    val userId: String?,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("isCompletedSignUp")
    val isCompletedSignUp: Boolean,
)
