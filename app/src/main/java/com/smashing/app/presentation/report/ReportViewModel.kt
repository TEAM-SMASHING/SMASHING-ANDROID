package com.smashing.app.presentation.report

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.moderation.ReportSubmitResult
import com.smashing.app.data.repository.api.ModerationRepository
import com.smashing.app.data.type.ReportType
import com.smashing.app.presentation.report.navigation.ReportPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moderationRepository: ModerationRepository,
) : ViewModel() {

    private val reportedUserProfileId = savedStateHandle.toRoute<ReportPage>().reportedUserProfileId

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
            moderationRepository.postReportUser(
                reportedUserProfileId = reportedUserProfileId,
                reportTypeCode = type.toString(),
                reasonDetail = reasonDetail,
            ).let { result ->
                when (result) {
                    ReportSubmitResult.Success -> {
                        updateReportUiState(ReportUiState.Success)
                        _sideEffect.emit(ReportContract.SideEffect.ReportSubmitted)
                    }

                    ReportSubmitResult.AlreadyReported -> {
                        _sideEffect.emit(ReportContract.SideEffect.ReportAlreadyReported)
                    }

                    is ReportSubmitResult.Failure -> {
                        updateReportUiState(
                            ReportUiState.Failure(msg = result.message?.message ?: "신고에 실패했습니다."),
                        )
                    }
                }
            }
        }
    }
}
