package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginResponse(
    @SerialName("accessToken")
    val accessToken: String?,
    @SerialName("refreshToken")
    val refreshToken: String?,
    @SerialName("authId")
    val authId: String,
)
