package com.smashing.app.presentation.report

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.repository.api.ReportRepository
import com.smashing.app.presentation.report.navigation.ReportPage
import com.smashing.app.data.type.ReportType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reportRepository: ReportRepository,
) : ViewModel() {

    private val reportedUserId = savedStateHandle.toRoute<ReportPage>().reportedUserId

    private val _uiState = MutableStateFlow(ReportContract.State())
    val uiState = _uiState.asStateFlow()

    val detailTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedReportType(reportType: ReportType) {
        _uiState.update { it.copy(selectedReportType = reportType) }
    }

    fun submitReport() {
        val type = _uiState.value.selectedReportType ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(reportUiState = ReportUiState.Loading) }
            val reasonDetail = if (type == ReportType.ETC) {
                detailTextFieldState.text.toString().trim().takeIf { it.isNotEmpty() }
            } else {
                null
            }
            reportRepository.postReportUser(
                reportedUserId = reportedUserId,
                reportTypeCode = type.toString(),
                reasonDetail = reasonDetail,
            ).onSuccess {
                _uiState.update { it.copy(reportUiState = ReportUiState.Success) }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        reportUiState = ReportUiState.Failure(
                            msg = e.message ?: "신고에 실패했습니다.",
                        ),
                    )
                }
            }
        }
    }

    fun consumeReportUiState() {
        _uiState.update { it.copy(reportUiState = ReportUiState.Idle) }
    }
}
