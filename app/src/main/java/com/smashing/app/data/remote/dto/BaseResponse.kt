package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val HTTP_OK = 200
private const val HTTP_ACCEPTED = 202

@Serializable
data class BaseResponse<T>(
    @SerialName("status")
    val status: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("data")
    val data: T?,
    @SerialName("timestamp")
    val timestamp: String,
)

fun <T> BaseResponse<T>.requireData(): T {
    if (statusCode != HTTP_OK || statusCode != HTTP_ACCEPTED) throw IllegalStateException("API request failed.")
    return data ?: throw IllegalStateException("Successful response but data was null.")
}
