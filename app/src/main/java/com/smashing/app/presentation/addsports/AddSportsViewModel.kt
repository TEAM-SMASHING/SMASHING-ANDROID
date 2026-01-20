package com.smashing.app.presentation.addsports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.type.SkillType
import com.smashing.app.data.type.SportType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList
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
    private val addSportsRepository: MyRepository,
    private val myRepository: MyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSportsContract.State())
    val uiState: StateFlow<AddSportsContract.State> = _uiState.asStateFlow()
    private val _sideEffect = MutableSharedFlow<AddSportsUiState.AddSportsSideEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchAvailableSports()
    }

    private fun fetchAvailableSports() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = AddSportsUiState.Loading) }
            myRepository.getMyPageInfo()
                .onSuccess { myPageData ->
                    val myExistingSportCodes: List<String> = myPageData.sportProfiles.map {
                        it.sportType.code
                    }

                    val filteredSports = SportType.entries.filter { sport ->
                        sport.code !in myExistingSportCodes
                    }.toImmutableList()

                    _uiState.update {
                        it.copy(
                            loadState = AddSportsUiState.Success,
                            availableSports = filteredSports
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            loadState = AddSportsUiState.Failure(
                                exception.message ?: "정보를 불러오는데 실패했습니다."
                            ),
                            availableSports = SportType.entries.toImmutableList()
                        )
                    }
                }
        }
    }

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
        val currentInfo = uiState.value.addSportsInfo
        if (currentInfo.selectedSports == null || currentInfo.selectedSkill == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(loadState = AddSportsUiState.Loading) }
            addSportsRepository.addSportsProfile(currentInfo)
                .onSuccess {
                    _uiState.update { it.copy(loadState = AddSportsUiState.Success) }
                    _sideEffect.emit(AddSportsUiState.AddSportsSideEffect.NavigateToSports)
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            loadState = AddSportsUiState.Failure(
                                exception.message ?: "오류가 발생했습니다."
                            )
                        )
                    }
                }
        }
    }
}
