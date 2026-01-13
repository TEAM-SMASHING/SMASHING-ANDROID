package com.smashing.app.presentation.submit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(getDummyState())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedDropdownItem(dropdownItem: String) = _uiState.update { state ->
        val isSubmitterWinner = dropdownItem == state.submitterName
        state.copy(
            selectedDropdownItem = dropdownItem,
            winnerUserId = if (isSubmitterWinner) state.submitterUserId else state.receiverUserId,
            loserUserId = if (isSubmitterWinner) state.receiverUserId else state.submitterUserId,
            isButtonEnabled = isScoreMatchingWinner(
                isSubmitterWinner = isSubmitterWinner,
                submitterScore = state.submitterScore,
                receiverScore = state.receiverScore,
            ),
        )
    }

    fun updateSubmitterScore(score: Int) = _uiState.update { state ->
        val isSubmitterWinner = state.selectedDropdownItem == state.submitterName
        state.copy(
            submitterScore = score,
            isButtonEnabled = state.selectedDropdownItem != null && isScoreMatchingWinner(
                isSubmitterWinner = isSubmitterWinner,
                submitterScore = score,
                receiverScore = state.receiverScore,
            ),
        )
    }

    fun updateReceiverScore(score: Int) = _uiState.update { state ->
        val isSubmitterWinner = state.selectedDropdownItem == state.submitterName
        state.copy(
            receiverScore = score,
            isButtonEnabled = state.selectedDropdownItem != null && isScoreMatchingWinner(
                isSubmitterWinner = isSubmitterWinner,
                submitterScore = state.submitterScore,
                receiverScore = score,
            ),
        )
    }

    private fun isScoreMatchingWinner(
        isSubmitterWinner: Boolean,
        submitterScore: Int,
        receiverScore: Int,
    ): Boolean {
        return if (isSubmitterWinner) {
            submitterScore > receiverScore
        } else {
            receiverScore > submitterScore
        }
    }

    private fun getDummyState(): SubmitContract.State {
        return SubmitContract.State(
            selectedDropdownItem = null,
            submitterName = "밤이달이",
            receiverName = "와쿠와쿠",
            submitterScore = 0,
            receiverScore = 0,
            submitterUserId = "",
            receiverUserId = "",
            winnerUserId = null,
            loserUserId = null,
            isButtonEnabled = false,
        )
    }
}
