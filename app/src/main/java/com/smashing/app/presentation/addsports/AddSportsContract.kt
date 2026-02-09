package com.smashing.app.presentation.addsports

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.type.SportType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
class AddSportsContract {
    @Immutable
    data class State(
        val currentStep: Int = 1,
        val loadState: AddSportsUiState = AddSportsUiState.Idle,
        val addSportsInfo: AddSportsInfo = AddSportsInfo(
            selectedSports = null,
            selectedSkill = null,
        ),
        val availableSports: ImmutableList<SportType> = persistentListOf(),
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

