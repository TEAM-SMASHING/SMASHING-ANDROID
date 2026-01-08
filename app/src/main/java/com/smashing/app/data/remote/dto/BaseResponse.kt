package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("status")
    val status: String,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("data")
    val data: T?,
    @SerialName("timestamp")
    val timestamp: String,
)
