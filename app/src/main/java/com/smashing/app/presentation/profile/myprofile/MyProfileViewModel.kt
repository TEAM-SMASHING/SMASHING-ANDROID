package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.profile.myprofile.MyProfileContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
class MyProfileViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchProfileInfo()
    }

    private fun fetchProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = MyProfileUiState.Loading) }

            try {
                // TODO: 실제 API 호출 (delay로 시뮬레이션)
                delay(1000)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loadState = MyProfileUiState.Failure(e.message ?: "Unknown Error"))
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
        updateSelectedProfileId(profileId)

        viewModelScope.launch {
            // TODO fetch 함수 호출
        }
    }

    private fun updateSelectedProfileId(profileId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSportProfileId = profileId,
            )
        }
    }

    // TODO: 추후 제거 예정
    private fun getDummyState(): State {
        val loadState: MyProfileUiState = MyProfileUiState.Success
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

        val gameReview: ImmutableList<GameReview> = persistentListOf(
            GameReview(
                gameReviewId = "",
                "이야이야오",
                "",
                "매너도 좋고, 너무 잘하세요!",

                ),
            GameReview(
                gameReviewId = "",
                "이야이야오",
                "",
                "매너도 좋고, 너무 잘하세요!",

                ),
            GameReview(
                gameReviewId = "",
                "이야이야오",
                "",
                "매너도 좋고, 너무 잘하세요!",

                ),
            GameReview(
                gameReviewId = "",
                "이야이야오",
                "",
                "매너도 좋고, 너무 잘하세요!",

                ),
            GameReview(
                gameReviewId = "",
                "이야이야오",
                "",
                "매너도 좋고, 너무 잘하세요!",

                ),
        )

        return State(
            loadState = loadState,
            profileInfo = profileInfo,
            sportProfileList = persistentListOf(
                SportProfile(
                    profileId = "1",
                    sportType = SportType.PING_PONG,
                    isActive = true,
                ),
            ),
            gameReview = gameReview,
            )
    }
}
