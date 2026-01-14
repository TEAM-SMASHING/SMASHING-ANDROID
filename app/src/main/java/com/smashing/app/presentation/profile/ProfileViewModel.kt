package com.smashing.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.data.model.profile.RatingCount
import com.smashing.app.data.model.profile.Review
import com.smashing.app.data.model.profile.TagCount
import com.smashing.app.data.model.profile.UserProfileInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(getDummyState())
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
                    mySports = currentState.profileInfo.mySports
                )
            )
            currentState.copy(profileInfo = currentState.profileInfo)
        }
        fetchProfileData()
    }

    // TODO: 추후 제거 예정
    private fun getDummyState(): ProfileContract.State {
        val loadState: ProfileUiState = ProfileUiState.Success
        val profileInfo = UserProfileInfo(
            tierType = TierType.GOLD_1,
            mySports = listOf(SportType.PING_PONG, SportType.BADMINTON),
            selectedSport = SportType.PING_PONG,
            lpProgress = 0.1f,
            minLp = 100,
            maxLp = 500,
            winCount = 4,
            loseCount = 5,
        )

        val reviews: ImmutableList<Review> = persistentListOf(
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!",
                userId = "3",
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!",
                userId = "5",
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!",
                userId = "6",
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!",
                userId = "7",
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!",
                userId = "8",
            ),
        )
        val reviewRate = RatingCount(0, 100, 3)
        val tagCount = TagCount(5, 56, 100, 2)

        return ProfileContract.State(
            loadState = loadState,
            profileInfo = profileInfo,
            reviews = reviews,
            reviewRate = reviewRate,
            tagCount = tagCount,
        )

    }
}
