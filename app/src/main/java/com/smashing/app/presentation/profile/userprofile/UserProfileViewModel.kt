package com.smashing.app.presentation.profile.userprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.presentation.profile.navigation.UserProfile
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.SideEffect.NavigateToAllReview
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.UserProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val matchingRepository: MatchingRepository,
) : ViewModel() {

    private val userInfo = savedStateHandle.toRoute<UserProfile>()

    private val userId = userInfo.userId
    private val sportCode = userInfo.sportCode

    private val _uiState = MutableStateFlow(UserProfileContract.State())
    val uiState: StateFlow<UserProfileContract.State> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UserProfileContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchUserRecentReviewStats()
        fetchProfileInfo()
        fetchUserProfileReview()
    }

    private fun fetchProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = UserProfileUiState.Loading) }
            userRepository.getUserInfoDetail(
                userId = userId,
                sportCode = sportCode,
            ).onSuccess { data ->
                Timber.tag("userprofile").d("${data.isAcceptable}")
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = UserProfileUiState.Success,
                        isChallengeable = data.isChallengeable,
                        isAcceptable = data.isAcceptable,
                        receivedMatchingId = data.receivedMatchingId,
                        profileInfo = data.profileInfo,
                        sportProfileList = data.sportProfile.toImmutableList(),
                        selectedSportProfileId = data.sportProfile.find { it.isActive }?.profileId
                            ?: data.profileInfo.profileId
                    )
                }
                Timber.tag("userprofile").d("${data.isAcceptable}")
                Timber.tag("userprofile").d("${_uiState.value.isAcceptable}")
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        loadState = UserProfileUiState.Failure(
                            exception.message ?: "오류 발생"
                        )
                    )
                }
            }
        }
    }

    fun navigateToAllReview() = viewModelScope.launch {
        _sideEffect.emit(
            NavigateToAllReview(userId)
        )
    }

    fun fetchUserRecentReviewStats() = viewModelScope.launch {
        userRepository.getUserRecentReviewStats(
            userId = userId,
            sportCode = sportCode,
        ).onSuccess { data ->
            _uiState.update { currentState ->
                currentState.copy(
                    loadState = UserProfileUiState.Success,
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
                    loadState = UserProfileUiState.Failure(
                        exception.message ?: "오류 발생"
                    )
                )
            }
        }
    }

    fun fetchUserProfileReview() = viewModelScope.launch {

        _uiState.update { it.copy(userProfileUiState = UserProfileUiState.Loading) }

        reviewRepository.getUserRecentReviewList(
            userId = userId,
            sportCode = sportCode,
            cursor = null,
            size = CURSOR_SIZE,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                state.copy(
                    gameReview = cursorPage.items.toImmutableList(),
                    userProfileCursor = cursorPage.cursor,
                    userProfileUiState = if (cursorPage.items.isEmpty()) {
                        UserProfileUiState.Idle
                    } else {
                        UserProfileUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    userProfileUiState = UserProfileUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    fun showDialog() {
        _uiState.update {
            it.copy(isDialogVisible = true)
        }
    }

    fun dismissDialog() {
        _uiState.update {
            it.copy(isDialogVisible = false)
        }
    }

    companion object {
        private const val CURSOR_SIZE = 3
    }

    fun onYesClick() {
        viewModelScope.launch {
            // TODO: 매칭 수락 API 호출
            _uiState.update {
                it.copy(isMatchingRequest = false)
            }
        }
    }

    fun onNoClick() {
        viewModelScope.launch {
            // TODO: 매칭 거절/건너뛰기 API 호출

            _uiState.update {
                it.copy(
                    isMatchingRequest = false,
                )
            }
        }
    }

    fun requestCompetition() {

        viewModelScope.launch {
            _uiState.update { it.copy(loadState = UserProfileUiState.Loading) }
            matchingRepository.postMatching(
                receiverProfileId = _uiState.value.selectedSportProfileId
            ).onSuccess { data ->
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = UserProfileUiState.Success,
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        loadState = UserProfileUiState.Failure(
                            exception.message ?: "오류 발생"
                        )
                    )
                }
            }
        }
        showDialog()
    }
}
