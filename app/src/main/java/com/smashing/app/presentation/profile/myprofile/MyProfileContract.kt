package com.smashing.app.presentation.profile.myprofile

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface MyProfileContract {
    @Immutable
    data class State(
        val loadState: MyProfileUiState = MyProfileUiState.Idle,
        val profileInfo: ProfileInfo = ProfileInfo(),
        val sportProfileList: ImmutableList<SportProfile> = persistentListOf(),
        val selectedSportProfileId: String = "",
        val gameReview: ImmutableList<GameReview> = persistentListOf(),
        val gameReviewResult: GameReviewResult = GameReviewResult(),
    ) {
        val isReviewEmpty: Boolean
            get() = gameReview.isEmpty() && gameReviewResult.isStatsEmpty
    }

    sealed interface MyProfileUiState {
        data object Idle : MyProfileUiState
        data object Loading : MyProfileUiState
        data object Success : MyProfileUiState
        data class Failure(
            val msg: String,
        ) : MyProfileUiState
    }

    sealed interface SideEffect {
        data class NavigateToAllReview(val userId: String?): SideEffect
    }
}
