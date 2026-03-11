package com.smashing.app.presentation.report

import androidx.compose.runtime.Immutable
import com.smashing.app.presentation.report.type.ReportType

interface ReportContract {
    @Immutable
    data class State(
        val reportUiState: ReportUiState = ReportUiState.Idle,
        val selectedReportType: ReportType? = null,
    )
}

sealed interface ReportUiState{
    object Idle : ReportUiState

    object Loading : ReportUiState

    object Success : ReportUiState

    data class Failure(
        val msg: String,
    ) : ReportUiState
}