package com.smashing.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.type.SportType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileContract.State())
    val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    init {
        fetchProfileData()
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = ProfileUiState.Loading) }

            try {
                // TODO: 실제 API 호출 (delay로 시뮬레이션)
                delay(1000)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loadState = ProfileUiState.Failure(e.message ?: "Unknown Error"))
                }
            }
        }
    }

    fun updateSelectedSport(sport: SportType) {
        val currentInfo = _uiState.value.profileInfo

        if (currentInfo.selectedSport == sport) return

        _uiState.update { currentState ->
            currentState.copy(
                profileInfo = currentInfo.copy(
                    selectedSport = sport,
                    mySports = currentState.profileInfo.mySports.sortedByDescending { it == sport }
                        .toImmutableList()
                )
            )
            currentState.copy(profileInfo = currentState.profileInfo)
        }
        fetchProfileData()
    }
}
