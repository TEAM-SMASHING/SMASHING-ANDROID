package com.smashing.app.presentation.profile.addsports

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.profile.AddSportsInfo

class AddSportsContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val loadState: AddSportsUiState = AddSportsUiState.Idle,
        val addSportsInfo: AddSportsInfo = AddSportsInfo(
            selectedSports = emptyList(),
            selectedSkill = null,
        )
    ) {
        val isBtnEnabled: Boolean
            get() = when (currentStep) {
                1 -> addSportsInfo.selectedSports.isNotEmpty()
                2 -> addSportsInfo.selectedSkill != null
                else -> false
            }
    }
}


sealed interface AddSportsUiState {
    data object Idle : AddSportsUiState
    data object Loading : AddSportsUiState
    data object Success : AddSportsUiState
    data class Failure(
        val msg: String,
    ) : AddSportsUiState

    sealed interface AddSportsSideEffect {
        data object NavigateToSports : AddSportsSideEffect
    }
}

