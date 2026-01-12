package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val HTTP_OK = 200

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

fun <T> BaseResponse<T>.requireData(): T {
    if (statusCode != HTTP_OK) throw IllegalStateException("API request failed.")
    return data ?: throw IllegalStateException("Successful response but data was null.")
}
