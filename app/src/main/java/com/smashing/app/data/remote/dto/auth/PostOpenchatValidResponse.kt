package com.smashing.app.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostOpenchatValidResponse(
    @SerialName("valid")
    val valid: Boolean,
)
