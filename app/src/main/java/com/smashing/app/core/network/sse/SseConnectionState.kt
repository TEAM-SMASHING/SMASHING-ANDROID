package com.smashing.app.core.network.sse

sealed class SseConnectionState {
    data object Connected : SseConnectionState()

    data object Disconnected : SseConnectionState()

    data class Error(
        val error: Throwable?,
        val statusCode: Int?,
    ) : SseConnectionState()

    data class Retrying(
        val attempt: Int,
        val delayMs: Long,
    ) : SseConnectionState()

    override fun toString(): String = when (this) {
        is Connected -> "Connected"
        is Disconnected -> "Disconnected (normal)"
        is Error -> "Disconnected (error) - status: $statusCode, error: ${error?.message}"
        is Retrying -> "Retrying - attempt $attempt, delayMs: $delayMs"
    }
}
