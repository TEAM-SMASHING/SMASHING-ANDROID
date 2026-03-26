package com.smashing.app.data.model.moderation

sealed interface ReportSubmitResult {
    data object Success : ReportSubmitResult
    data object AlreadyReported : ReportSubmitResult
    data class Failure(val message: Throwable?) : ReportSubmitResult
}