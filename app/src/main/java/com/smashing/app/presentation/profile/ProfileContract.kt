package com.smashing.app.presentation.profile

import androidx.compose.runtime.Immutable
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.data.model.profile.RatingCount
import com.smashing.app.data.model.profile.Review
import com.smashing.app.data.model.profile.TagCount
import com.smashing.app.data.model.profile.UserProfileInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

interface ProfileContract {
    @Immutable
    data class State(
        val loadState: ProfileUiState = ProfileUiState.Idle,
        //TODO UserProfileCard State 추가
        val profileInfo: UserProfileInfo = UserProfileInfo(
            tierType = TierType.GOLD_1,
            tierIconResId = ic_fake_red,
            mySports = listOf(SportType.PING_PONG, SportType.BADMINTON),
            selectedSport = SportType.PING_PONG,
            lpProgress = 0.1f,
            minLp = 100,
            maxLp = 500,
            winCount = 4,
            loseCount = 5,
        ),
        val reviews: ImmutableList<Review> = persistentListOf(
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!"
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!"
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!"
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!"
            ),
            Review(
                gameId = "1",
                reviewId = "r1",
                opponentNickname = "닝우닝",
                confirmedAt = LocalDateTime.now().minusDays(2),
                content = "매너도 좋고, 너무 잘하세요!"
            ),
        ),
        val reviewRate: RatingCount = RatingCount(0, 100, 3),
        val tagCount: TagCount= TagCount(5,56,100,2),
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
