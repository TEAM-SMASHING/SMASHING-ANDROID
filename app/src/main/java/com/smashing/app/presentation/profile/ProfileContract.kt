package com.smashing.app.presentation.profile

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.data.model.profile.RatingCount
import com.smashing.app.data.model.profile.Review
import com.smashing.app.data.model.profile.TagCount
import com.smashing.app.data.model.profile.UserProfileInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface ProfileContract {
    @Immutable
    data class State(
        val loadState: ProfileUiState = ProfileUiState.Idle,
        val profileInfo: UserProfileInfo = UserProfileInfo(
            tierType = TierType.BRONZE_1,
            mySports = listOf(SportType.PING_PONG),
            selectedSport = SportType.PING_PONG,
            lpProgress = 0f,
            minLp = 0,
            maxLp = 0,
            winCount = 0,
            loseCount = 0,
        ),
        val reviews: ImmutableList<Review> = persistentListOf(),
        val reviewRate: RatingCount = RatingCount(0, 0, 0),
        val tagCount: TagCount = TagCount(0, 0, 0, 0),
    )
}

sealed interface ProfileUiState {
    data object Idle : ProfileUiState
    data object Loading : ProfileUiState
    data object Success : ProfileUiState
    data class Failure(
        val msg: String,
    ) : ProfileUiState
}
