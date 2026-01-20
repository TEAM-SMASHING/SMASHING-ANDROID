package com.smashing.app.presentation.profile.userprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.profile.navigation.UserProfile
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.SideEffect.NavigateToAllReview
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.UserProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<UserProfile>().userId

    private val _uiState = MutableStateFlow(UserProfileContract.State())
    val uiState: StateFlow<UserProfileContract.State> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UserProfileContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchUserProfileReview()
    }

    private fun fetchProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = UserProfileUiState.Loading) }

            try {
                // TODO: 실제 API 호출 (delay로 시뮬레이션)
                delay(1000)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loadState = UserProfileUiState.Failure(e.message ?: "Unknown Error"))
                }
            }
        }
    }

    fun navigateToAllReview() = viewModelScope.launch {
        _sideEffect.emit(
            NavigateToAllReview(userId)
        )
    }

    fun fetchUserProfileReview() = viewModelScope.launch {

        val currentState = _uiState.value

        _uiState.update { it.copy(userProfileUiState = UserProfileUiState.Loading) }

        userRepository.getUserRecentList(
            userId = userId,
            sportCode = "BM", //Todo: 실제 값으로 수정
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

    companion object {
        private const val CURSOR_SIZE = 3
    }

    // TODO: 추후 제거 예정
    private fun getDummyState(): UserProfileContract.State {
        val loadState: UserProfileUiState = UserProfileUiState.Success
        val profileInfo = ProfileInfo(
            profileId = "123",
            sportType = SportType.PING_PONG,
            genderType = GenderType.MALE,
            nickname = "하이하이",
            tierType = TierType.GOLD_1,
            minLp = 100,
            maxLp = 500,
            winCount = 4,
            loseCount = 5,
            lp = 3,
            reviewCount = 323,
        )
        return UserProfileContract.State(
            loadState = loadState,
            profileInfo = profileInfo,
            sportProfileList = persistentListOf(
                SportProfile(
                    profileId = "1",
                    sportType = SportType.PING_PONG,
                    isActive = true,
                ),
            )
        )
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
                    isCompeteButtonEnabled = true
                )
            }
        }
    }

    fun requestCompetition() {
        if (!_uiState.value.isCompeteButtonEnabled) return

        viewModelScope.launch {
            // TODO: 경쟁 신청 API 호출
            _uiState.update {
                it.copy(
                    isCompeteButtonEnabled = false,
                )
            }
        }
    }
}
