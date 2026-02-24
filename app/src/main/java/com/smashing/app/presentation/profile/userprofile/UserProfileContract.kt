package com.smashing.app.presentation.profile.userprofile

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.profile.user.UserProfileInfo
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class UserProfileContract {
    @Immutable
    data class State(
        val loadState: UserProfileUiState = UserProfileUiState.Idle,
        val userProfileInfo: UserProfileInfo,
        val selectedSportProfileId: String = "",
        val gameReview: ImmutableList<GameReview> = persistentListOf(),
        val gameReviewResult: GameReviewResult = GameReviewResult(),
        val userId: String = "",
        val isMatchingRequest: Boolean = true,
        val userProfileCursor: Cursor = Cursor(),
        val isDialogVisible: Boolean = false,
    ) {
        val activeProfile: ProfileInfo
            get() = userProfileInfo.userProfileInfo

        val sportProfileList: List<ProfileItem>
            get() = userProfileInfo.userProfileItem

        val isChallengeable: Boolean
            get() = userProfileInfo.isChallengeable

        val isAcceptable: Boolean
            get() = userProfileInfo.isAcceptable

        val receivedMatchingId: String?
            get() = userProfileInfo.receivedMatchingId

        val isReviewEmpty: Boolean
            get() = gameReview.isEmpty() && gameReviewResult.isStatsEmpty
    }

    sealed interface SideEffect {
        data class NavigateToAllReview(val userId: String?) : SideEffect
        data class ShowToast(val content: String) : SideEffect
    }
}

sealed interface UserProfileUiState {
    data object Idle : UserProfileUiState
    data object Loading : UserProfileUiState
    data object Success : UserProfileUiState
    data class Failure(
        val msg: String,
    ) : UserProfileUiState
}
