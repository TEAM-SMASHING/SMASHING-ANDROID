package com.smashing.app.presentation.addsports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class AddSportsViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSportsContract.State())
    val uiState: StateFlow<AddSportsContract.State> = _uiState.asStateFlow()
    private val _sideEffect = MutableSharedFlow<AddSportsUiState.AddSportsSideEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sideEffect = _sideEffect.asSharedFlow()
    fun updateSelectedSport(sport: SportType) {
        _uiState.update { state ->
            state.copy(
                addSportsInfo = state.addSportsInfo.copy(
                    selectedSports = sport,
                )
            )
        }
    }

    fun updateSelectedSkill(skill: SkillType) {
        _uiState.update { state ->
            state.copy(
                addSportsInfo = state.addSportsInfo.copy(
                    selectedSkill = skill,
                )
            )
        }
    }

    fun updateCurrentStep() {
        _uiState.update { it.copy(currentStep = it.currentStep + 1) }
    }

    fun postAddSport() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = AddSportsUiState.Loading) }
            // TODO: 실제 API 호출로 교체 필요
            //repository.addSport(uiState.value.addSportsInfo)
            //     .onSuccess { ... }
            //     .onFailure { ... }
            _uiState.update { it.copy(loadState = AddSportsUiState.Success) }
            _sideEffect.emit(AddSportsUiState.AddSportsSideEffect.NavigateToSports)
        }
    }
}
