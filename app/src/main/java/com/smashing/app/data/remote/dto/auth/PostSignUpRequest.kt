package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpRequest(
    @SerialName("socialId")
    val socialId: String,
    @SerialName("provider")
    val provider: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("openChatUrl")
    val openChatUrl: String,
    @SerialName("sportCode")
    val sportCode: String,
    @SerialName("experienceRange")
    val experienceRange: String,
    @SerialName("region")
    val region: String,
)
