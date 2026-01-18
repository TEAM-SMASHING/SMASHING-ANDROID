package com.smashing.app.presentation.profile.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    // private val userRepository: UserRepository // 실제 데이터 호출 시 필요
) : ViewModel() {
    private val _uiState = MutableStateFlow(getDummyState())

    val uiState: StateFlow<UserProfileContract.State> = _uiState.asStateFlow()

    init {
        fetchProfileInfo()
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
