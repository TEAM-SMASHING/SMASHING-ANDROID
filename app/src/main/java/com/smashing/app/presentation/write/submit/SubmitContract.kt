package com.smashing.app.presentation.write.submit

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.model.PlayerInfo
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

interface SubmitContract {
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
        val submitUiState: SubmitUiState = SubmitUiState.Idle,
        val isResubmitDialogVisible: Boolean = false,
        val isConfirmDialogOpen: Boolean = false,
        val isSubmitAvailable: Boolean = false,
    )

    sealed interface SideEffect {
        data object NavigateToMatching : SideEffect
    }

    sealed interface SubmitUiState {
        data object Idle : SubmitUiState
        data object Loading : SubmitUiState
        data object Success : SubmitUiState
        data class Failure(val msg: String) : SubmitUiState
    }
}
