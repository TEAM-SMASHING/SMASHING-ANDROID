package com.smashing.app.presentation.write.submit

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.model.MatchPlayer
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

interface SubmitContract {
    @Immutable
    data class State(
        val submitter: MatchPlayer = MatchPlayer("", ""),
        val receiver: MatchPlayer = MatchPlayer("", ""),
        val submitterScore: Int = 0,
        val receiverScore: Int = 0,
        val winner: MatchPlayer? = null,
        val loser: MatchPlayer? = null,
        val isButtonEnabled: Boolean = false,
        val selectedRatingTypes: ImmutableSet<ReviewRatingType> = persistentSetOf(),
        val selectedTagTypes: ImmutableSet<ReviewTagType> = persistentSetOf(),
        val reviewText: String = "",
        val reviewId: String = "",
        val submitUiState: SubmitUiState = SubmitUiState.Idle,
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface SubmitUiState {
        data object Idle : SubmitUiState
        data object Loading : SubmitUiState
        data object Success : SubmitUiState
        data class Failure(val msg: String) : SubmitUiState
    }
}
