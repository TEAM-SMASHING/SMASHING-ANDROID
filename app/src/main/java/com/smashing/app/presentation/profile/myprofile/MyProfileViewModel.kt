package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.repository.api.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MyProfileContract.State(
            loadState = MyProfileUiState.Loading,
            profileInfo = ProfileInfo(),
            sportProfileList = persistentListOf(),
            gameReview = persistentListOf()
        )
    )
    val uiState: StateFlow<MyProfileContract.State> = _uiState.asStateFlow()

    init {
        fetchProfileInfo()
    }

    private fun fetchProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = MyProfileUiState.Loading) }

            myRepository.getMyPageInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            loadState = MyProfileUiState.Success,
                            profileInfo = data.profileInfo,
                            sportProfileList = data.sportProfiles.toPersistentList(),
                            selectedSportProfileId = data.sportProfiles.find { it.isActive }?.profileId
                                ?: data.profileInfo.profileId
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            loadState = MyProfileUiState.Failure(
                                exception.message ?: "알 수 없는 오류가 발생했습니다."
                            )
                        )
                    }
                }
        }
    }

    fun selectProfileId(profileId: String) {
        if (uiState.value.selectedSportProfileId == profileId) return
        viewModelScope.launch {
            myRepository.switchActiveMyProfile(profileId)
                .onSuccess {
                    updateSelectedProfileId(profileId)
                    fetchProfileInfo()
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            loadState = MyProfileUiState.Failure(
                                exception.message ?: "알 수 없는 오류가 발생했습니다."
                            )
                        )
                    }
                }
        }
    }

    private fun updateSelectedProfileId(profileId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSportProfileId = profileId,
            )
        }
    }
}
