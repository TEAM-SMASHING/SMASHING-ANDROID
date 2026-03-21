package com.smashing.app.presentation.report

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReportType

interface ReportContract {
    @Immutable
    data class State(
        val reportUiState: ReportUiState = ReportUiState.Idle,
        val selectedReportType: ReportType? = null,
    )

    sealed interface SideEffect {
        data object ReportSubmitted : SideEffect

        data object ReportAlreadyReported : SideEffect
    }
}

sealed interface ReportUiState {
    data object Idle : ReportUiState

    data object Loading : ReportUiState

    data object Success : ReportUiState

    data class Failure(
        val msg: String,
    ) : ReportUiState
}
