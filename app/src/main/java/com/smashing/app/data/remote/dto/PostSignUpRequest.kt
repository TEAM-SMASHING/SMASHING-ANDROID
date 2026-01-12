package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpRequest(
    @SerialName("auth_id")
    val authId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("open_chat_url")
    val openChatUrl: String,
    @SerialName("sportode")
    val sportCode: String,
    @SerialName("tier")
    val tier: String,
    @SerialName("region")
    val region: String,
)
