package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginRequest(
    @SerialName("accessToken")
    val accessToken: String,
)
