package com.smashing.app.presentation.write.confirm

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.smashing.app.core.common.type.ReviewRatingType
import com.smashing.app.core.common.type.ReviewTagType
import com.smashing.app.presentation.write.model.MatchPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(getDummyState())
    val uiState = _uiState.asStateFlow()
    val reviewTextFieldState: TextFieldState = TextFieldState()
    val leftTextFieldState: TextFieldState = TextFieldState()
    val rightTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedRatingType(type: ReviewRatingType) = _uiState.update { state ->
        val next = if (type in state.selectedRatingTypes)
            state.selectedRatingTypes - type
        else state.selectedRatingTypes + type

        state.copy(selectedRatingTypes = next.toImmutableSet())
    }


    private fun isScoreMatchingWinner(
        isSubmitterWinner: Boolean,
        submitterScore: Int,
        receiverScore: Int,
    ): Boolean = if (isSubmitterWinner) submitterScore > receiverScore
    else receiverScore > submitterScore


    fun updateSelectedTagType(type: ReviewTagType) = _uiState.update { state ->
        val next = if (type in state.selectedTagTypes)
            state.selectedTagTypes - type
        else state.selectedTagTypes + type

        state.copy(selectedTagTypes = next.toImmutableSet())
    }

    fun updateSelectedWinner(winnerName: String) = _uiState.update { state ->
        val isSubmitterWinner = winnerName == state.submitter.name
        val winner = if (isSubmitterWinner) state.submitter else state.receiver
        val loser = if (isSubmitterWinner) state.receiver else state.submitter
        state.copy(
            winner = winner,
            loser = loser,
            isButtonEnabled = isScoreMatchingWinner(
                isSubmitterWinner = isSubmitterWinner,
                submitterScore = state.submitterScore,
                receiverScore = state.receiverScore,
            ),
        )
    }

    fun updateReceiverScore(score: Int) = _uiState.update { state ->
        val isSubmitterWinner = state.winner?.userId == state.submitter.userId
        state.copy(
            receiverScore = score,
            isButtonEnabled = state.winner != null && isScoreMatchingWinner(
                isSubmitterWinner = isSubmitterWinner,
                submitterScore = state.submitterScore,
                receiverScore = score,
            ),
        )
    }

    fun updateSubmitterScore(score: Int) = _uiState.update { state ->
        val isSubmitterWinner = state.winner?.userId == state.submitter.userId
        state.copy(
            submitterScore = score,
            isButtonEnabled = state.winner != null && isScoreMatchingWinner(
                isSubmitterWinner = isSubmitterWinner,
                submitterScore = score,
                receiverScore = state.receiverScore,
            ),
        )
    }

    private fun getDummyState(): ConfirmContract.State {
        return ConfirmContract.State(
            submitter = MatchPlayer(userId = "1", name = "밤이달이"),
            receiver = MatchPlayer(userId = "2", name = "와쿠와쿠"),
            submitterScore = 0,
            receiverScore = 0,
            winner = null,
            loser = null,
            isButtonEnabled = false,
        )
    }


}

