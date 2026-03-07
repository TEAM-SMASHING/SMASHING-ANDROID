package com.smashing.app.presentation.report

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.smashing.app.presentation.report.type.ReportType
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(ReportContract.State())
    val uiState = _uiState.asStateFlow()

    val detailTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedReportType(reportType: ReportType) {
        _uiState.update { it.copy(selectedReportType = reportType) }
    }

    fun updateEtcText(text: String) {
        _uiState.update { it.copy(etcText = text.ifBlank { null }) }
    }
}