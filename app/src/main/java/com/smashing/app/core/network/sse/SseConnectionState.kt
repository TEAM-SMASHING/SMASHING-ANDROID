package com.smashing.app.core.network.sse

sealed class SseConnectionState {
    data object Connected : SseConnectionState()

    data object Disconnected : SseConnectionState()

    data class Error(
        val error: Throwable?,
        val retryAttempt: Int,
    ) : SseConnectionState()

    data class Retrying(
        val retryAttempt: Int,
        val maxRetries: Int,
        val isPeriodic: Boolean = false,
    ) : SseConnectionState()

    override fun toString(): String = when (this) {
        is Connected -> "Connected"
        is Disconnected -> "Disconnected (normal)"
        is Error -> "Disconnected (error) - attempt $retryAttempt, error: ${error?.message}"
        is Retrying -> if (isPeriodic) {
            "Retrying (periodic) - attempt $retryAttempt"
        } else {
            "Retrying - attempt $retryAttempt/$maxRetries"
        }
    }
}
