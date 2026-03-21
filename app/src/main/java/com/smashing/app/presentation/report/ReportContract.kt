package com.smashing.app.presentation.report

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReportType

interface ReportContract {
    @Immutable
    data class State(
        val isSubmitting: Boolean = false,
        val selectedReportType: ReportType? = null,
    )

    sealed interface SideEffect {
        data object ReportSubmitted : SideEffect

        data class ReportFailed(
            val message: String,
        ) : SideEffect
    }
}
