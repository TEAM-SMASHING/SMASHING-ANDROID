package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginRequest(
    @SerialName("idToken")
    val idToken: String,
    @SerialName("provider")
    val provider: String,
)
