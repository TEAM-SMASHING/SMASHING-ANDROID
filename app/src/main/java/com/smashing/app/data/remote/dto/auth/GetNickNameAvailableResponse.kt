package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetNickNameAvailableResponse(
    @SerialName("available")
    val available: Boolean,
)
