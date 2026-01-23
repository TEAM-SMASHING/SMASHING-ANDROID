package com.smashing.app.presentation.write.confirm

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.data.type.ConfirmDenyType
import com.smashing.app.presentation.write.model.PlayerInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

interface ConfirmContract {
    @Immutable
    data class State(
        val submitter: PlayerInfo = PlayerInfo("", "", 0),
        val receiver: PlayerInfo = PlayerInfo("", "", 0),
        val winnerId: String? = null,
        val isButtonEnabled: Boolean = false,
        val selectedRating: ReviewRatingType? = null,
        val selectedTagList: ImmutableSet<ReviewTagType> = persistentSetOf(),
        val reviewText: String = "",
        val reviewId: String = "",
        val confirmUiState: ConfirmUiState = ConfirmUiState.Idle,
        val rating: String = "",
        val tag: ImmutableList<String> = persistentListOf(),
        val content: String? = null,
        val showDenyBottomSheet: Boolean = false,
        val showRejectDialog: Boolean = false,
        val selectedDenyReason: ConfirmDenyType? = null,
        val showConfirmDialog: Boolean = false,
    )

    sealed interface SideEffect {
        sealed interface ConfirmResultSideEffect : SideEffect {
            data object NavigateBack : ConfirmResultSideEffect
        }

        sealed interface ConfirmReviewSideEffect : SideEffect {
            data object NavigateBack : ConfirmReviewSideEffect

            data class NavigateToConfirmReview(
                val reviewId: String
            ) : ConfirmReviewSideEffect
        }
    }

    sealed interface ConfirmUiState {
        data object Idle : ConfirmUiState
        data object Loading : ConfirmUiState
        data object Success : ConfirmUiState
        data class Failure(val msg: String) : ConfirmUiState
    }
}
