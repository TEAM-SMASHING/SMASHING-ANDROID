package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.presentation.profile.myprofile.MyProfileContract.MyProfileUiState
import com.smashing.app.presentation.profile.myprofile.MyProfileContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val myRepository: MyRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

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
                    fetchMyRecentReviewStats()
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

    fun fetchReviews() = viewModelScope.launch {
        _uiState.update {
            it.copy(reviewLoadState = MyProfileUiState.Loading)
        }

        reviewRepository.getMyGameReviewsResponse(
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

    fun fetchMyRecentReviewStats() = viewModelScope.launch {
        reviewRepository.getUserRecentReviewStats(
        ).onSuccess { data ->
            _uiState.update { currentState ->
                currentState.copy(
                    reviewLoadState = MyProfileUiState.Success,
                    gameReviewResult = GameReviewResult(
                        bestCount = data.bestCount,
                        goodCount = data.goodCount,
                        badCount = data.badCount,
                        goodMannerCount = data.goodMannerCount,
                        onTimeCount = data.onTimeCount,
                        fairPlayCount = data.fairPlayCount,
                        fastResponseCount = data.fastResponseCount,
                    ),
                )
            }
        }.onFailure { exception ->
            _uiState.update {
                it.copy(
                    reviewLoadState = MyProfileUiState.Failure(
                        exception.message ?: "오류 발생"
                    )
                )
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 3
    }
}
