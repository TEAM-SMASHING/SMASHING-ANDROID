package com.smashing.app.presentation.addsports

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.profile.my.AddSportsInfo

class AddSportsContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val loadState: AddSportsUiState = AddSportsUiState.Idle,
        val addSportsInfo: AddSportsInfo = AddSportsInfo(),
    ) {
        val isBtnEnabled: Boolean
            get() = when (currentStep) {
                1 -> addSportsInfo.selectedSports != null
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

