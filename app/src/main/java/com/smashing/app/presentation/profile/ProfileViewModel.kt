package com.smashing.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.R
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.data.model.ProfileReview
import com.smashing.app.data.model.UserProfileInfo
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
class ProfileViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileContract.State())
    val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    init {
        fetchProfileData()
    }

    // TODO 더미 데이터 삭제 예정
    private fun fetchProfileData() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = ProfileUiState.Loading) }

            try {
                // TODO: 실제 API 호출 (delay로 시뮬레이션)
                delay(1000)

                val dummyProfile = UserProfileInfo(
                    tierType = TierType.GOLD_1,
                    tierIconResId = R.drawable.ic_fake_red,
                    mySports = listOf(),
                    selectedSport = SportType.BADMINTON,
                    lpProgress = 0.2f,
                    minLp = 100,
                    maxLp = 500,
                    winCount = 12,
                    loseCount = 5,
                )

                val dummyReviews = persistentListOf(
                    ProfileReview(1, "닝우닝", "2일 전", "매너 굿!"),
                    ProfileReview(2, "스매싱", "4일 전", "너무 잘해요"),
                )

                _uiState.update {
                    it.copy(
                        loadState = ProfileUiState.Success,
                        profileInfo = dummyProfile,
                        reviews = dummyReviews,
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loadState = ProfileUiState.Failure(e.message ?: "Unknown Error"))
                }
            }
        }
    }

    fun updateSelectedSport(sport: SportType) {
        val currentInfo = _uiState.value.profileInfo ?: return

        if (currentInfo.selectedSport == sport) return

        // TODO: 실제로는 여기서 종목에 맞는 전적 데이터 서버 호출
        val newTier = if (sport == SportType.PING_PONG) TierType.GOLD_1 else TierType.SILVER_2
        val newWinCount = if (sport == SportType.PING_PONG) 12 else 5

        _uiState.update { currentState ->
            currentState.copy(
                profileInfo = currentInfo.copy(
                    selectedSport = sport,
                    tierType = newTier,
                    winCount = newWinCount,
                    //나머지 필드들도 필요시 업데이트
                )
            )
        }
    }
}
