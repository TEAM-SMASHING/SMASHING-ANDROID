package com.smashing.app.data.remote.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("status")
    val status: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("data")
    val data: T? = null,
    @SerialName("timestamp")
    val timestamp: String? = null,
)
