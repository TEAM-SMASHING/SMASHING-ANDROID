package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpRequest(
    @SerialName("kakaoId")
    val kakaoId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("openChatUrl")
    val openChatUrl: String,
    @SerialName("sportCode")
    val sportCode: String,
    @SerialName("tier")
    val tier: String,
    @SerialName("region")
    val region: String,
)
