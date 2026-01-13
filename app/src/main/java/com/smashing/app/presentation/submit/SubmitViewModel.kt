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

    fun updateSelectedDropdownItem(dropdownItem: String) = _uiState.update {
        it.copy(
            selectedDropdownItem = dropdownItem,
        )
    }

    fun updateSubmitterScore(score: Int) = _uiState.update {
        it.copy(submitterScore = score)
    }

    fun updateReceiverScore(score: Int) = _uiState.update {
        it.copy(receiverScore = score)
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
