package com.smashing.app.presentation.profile.userprofile

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class UserProfileContract {
    @Immutable
    data class State(
        val loadState: UserProfileUiState = UserProfileUiState.Idle,
        val profileInfo: ProfileInfo = ProfileInfo(
            profileId = "",
            nickname = "",
            genderType = GenderType.MALE,
            tierType = TierType.GOLD_1,
            lp = 0,
            minLp = 0,
            maxLp = 1,
            winCount = 0,
            loseCount = 0,
            reviewCount = 0,
            sportType = SportType.PING_PONG,
        ),
        val sportProfileList: ImmutableList<SportProfile> = persistentListOf(),
        val selectedSportProfileId: String = "",
        val gameReview: ImmutableList<GameReview> = persistentListOf(),
        val gameReviewResult: GameReviewResult = GameReviewResult(),
        val userId: String? = "",
        val isMatchingRequest: Boolean = true,
        val isCompeteButtonEnabled: Boolean = false,
    )
}


sealed interface UserProfileUiState {
    data object Idle : UserProfileUiState
    data object Loading : UserProfileUiState
    data object Success : UserProfileUiState
    data class Failure(
        val msg: String,
    ) : UserProfileUiState
}
