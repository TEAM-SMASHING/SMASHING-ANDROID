package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.presentation.profile.myprofile.MyProfileContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import com.smashing.app.data.repository.api.MyRepository
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(profileLoadState = MyProfileUiState.Loading) }
            myRepository.getMyPageInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            profileLoadState = MyProfileUiState.Success,
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
                            profileLoadState = MyProfileUiState.Failure(
                                exception.message ?: "오류 발생"
                            )
                        )
                    }
                }
        }
    }

    fun navigateToAllReview() = viewModelScope.launch {
        _sideEffect.emit(
            SideEffect.NavigateToAllReview(null)
        )
    }

    fun selectProfileId(profileId: String) {
        val currentState = uiState.value
        if (currentState.selectedSportProfileId == profileId) return

        val optimisticList = currentState.sportProfileList.map { profile ->
            if (profile.profileId == profileId) {
                profile.copy(isActive = true)
            } else {
                profile.copy(isActive = false)
            }
        }.toPersistentList()

        _uiState.update {
            it.copy(
                selectedSportProfileId = profileId,
                sportProfileList = optimisticList
            )
        }
        viewModelScope.launch {
            myRepository.switchActiveMyProfile(profileId)
                .onSuccess {
                    fetchProfileInfo()
                    fetchReviews()
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            profileLoadState = MyProfileUiState.Failure(
                                exception.message ?: "프로필 변경 실패"
                            )
                        )
                    }
                }
        }
    }

    fun fetchReviews() {

        viewModelScope.launch {
            _uiState.update {
                it.copy(reviewLoadState = MyProfileUiState.Loading)
            }

            myRepository.getMyGameReviews(
                cursor = null,
                size = PAGE_SIZE
            )
                .onSuccess { page ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            reviewLoadState = MyProfileUiState.Success,
                            gameReview = page.items.toPersistentList()
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            reviewLoadState = MyProfileUiState.Failure(
                                exception.message ?: "리뷰를 불러오는데 실패했습니다."
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


    companion object {
        private const val PAGE_SIZE = 3
    }
}
