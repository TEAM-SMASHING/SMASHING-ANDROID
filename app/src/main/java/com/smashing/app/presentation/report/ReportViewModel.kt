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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

private const val HTTP_STATUS_ALREADY_REPORTED = 409

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reportRepository: ReportRepository,
) : ViewModel() {

    private val reportedUserId = savedStateHandle.toRoute<ReportPage>().reportedUserId

    private val _uiState = MutableStateFlow(ReportContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ReportContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val detailTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedReportType(reportType: ReportType) = _uiState.update {
        it.copy(selectedReportType = reportType)
    }

    private fun updateReportUiState(reportUiState: ReportUiState) = _uiState.update {
        it.copy(reportUiState = reportUiState)
    }

    fun postReport() {
        val type = _uiState.value.selectedReportType ?: return
        viewModelScope.launch {
            updateReportUiState(ReportUiState.Loading)
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
                updateReportUiState(ReportUiState.Success)
                _sideEffect.emit(ReportContract.SideEffect.ReportSubmitted)
                updateReportUiState(ReportUiState.Idle)
            }.onFailure { throwable ->
                when {
                    throwable is HttpException && throwable.code() == HTTP_STATUS_ALREADY_REPORTED -> {
                        _sideEffect.emit(ReportContract.SideEffect.ReportAlreadyReported)
                        updateReportUiState(ReportUiState.Idle)
                    }

                    else -> {
                        updateReportUiState(
                            ReportUiState.Failure(msg = throwable.message ?: "신고에 실패했습니다."),
                        )
                    }
                }
            }
        }
    }
}
