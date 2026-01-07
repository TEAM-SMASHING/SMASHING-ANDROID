package com.smashing.app.data.remote.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val HTTP_OK = 200

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

fun <T> BaseResponse<T>.requireData(): T {
    if (statusCode != HTTP_OK) throw IllegalStateException("API request failed.")
    return data ?: throw IllegalStateException("Successful response but data was null.")
}
