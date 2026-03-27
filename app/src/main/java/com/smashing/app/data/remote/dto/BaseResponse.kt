package com.smashing.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException

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

@Serializable
data class NetworkErrorResponse(
    @SerialName("status")
    val status: String,
    @SerialName("statusCode")
    val statusCode: Int,
    @SerialName("data")
    val data: String? = null,
    @SerialName("message")
    val message: String,
    @SerialName("errorCode")
    val errorCode: String,
    @SerialName("errorName")
    val errorName: String,
    @SerialName("timestamp")
    val timestamp: String,
)

fun <T> BaseResponse<T>.requireData(): T {
    if (statusCode != HTTP_OK && statusCode != HTTP_ACCEPTED) throw IllegalStateException("API request failed.")
    return data ?: throw IllegalStateException("Successful response but data was null.")
}

fun Throwable.toNetworkErrorResponse(json: Json): NetworkErrorResponse {
    val httpException = this as? HttpException ?: throw this
    val errorBody = httpException.response()?.errorBody()?.string() ?: throw this
    return json.decodeFromString<NetworkErrorResponse>(errorBody)
}
