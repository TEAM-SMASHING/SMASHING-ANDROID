package com.smashing.app.presentation.profile.review

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ReviewContract {
    @Immutable
    data class State(
        val loadState: ReviewUiState = ReviewUiState.Idle,
        val gameReview: ImmutableList<GameReview> = persistentListOf(),
        val gameReviewResult: GameReviewResult = GameReviewResult(),
        val userId: String = "",
        val isMatchingRequest: Boolean = true,
        val isCompeteButtonEnabled: Boolean = false,
        val reviewUiState: ReviewUiState = ReviewUiState.Idle,
        val reviewCursor: Cursor = Cursor(),
    ) {
        val isReviewEmpty: Boolean
            get() = gameReview.isEmpty() && gameReviewResult.isStatsEmpty
    }


    sealed interface ReviewUiState {
        data object Idle : ReviewUiState
        data object Loading : ReviewUiState
        data object Success : ReviewUiState
        data class Failure(
            val msg: String,
        ) : ReviewUiState
    }
}
