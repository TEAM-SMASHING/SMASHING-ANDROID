package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.repository.api.GameRepository
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.model.MatchPlayer
import com.smashing.app.presentation.write.navigation.Submit
import com.smashing.app.presentation.write.submit.SubmitContract.SideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val gameId = savedStateHandle.toRoute<Submit>().gameId
    private val _uiState = MutableStateFlow(getDummyState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SubmitContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val leftTextFieldState: TextFieldState = TextFieldState()
    val rightTextFieldState: TextFieldState = TextFieldState()
    val reviewTextFieldState: TextFieldState = TextFieldState()

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

    private fun isScoreMatchingWinner(
        isSubmitterWinner: Boolean,
        submitterScore: Int,
        receiverScore: Int,
    ): Boolean = if (isSubmitterWinner) submitterScore > receiverScore
    else receiverScore > submitterScore

    fun updateSelectedRatingType(type: ReviewRatingType) = _uiState.update { state ->
        val next = if (type in state.selectedRatingTypes)
            state.selectedRatingTypes - type
        else state.selectedRatingTypes + type

        state.copy(selectedRatingTypes = next.toImmutableSet())
    }

    fun updateSelectedTagType(type: ReviewTagType) = _uiState.update { state ->
        val next = if (type in state.selectedTagTypes)
            state.selectedTagTypes - type
        else state.selectedTagTypes + type

        state.copy(selectedTagTypes = next.toImmutableSet())
    }

    fun submitGame() = viewModelScope.launch {
        val state = _uiState.value
        val winner = state.winner
        val loser = state.loser
        
        if (winner == null || loser == null) return@launch

        _uiState.update { it.copy(submitUiState = SubmitContract.SubmitUiState.Loading) }
        
        val review = if (state.selectedRatingTypes.isNotEmpty()) {
            PostGameSubmissionRequest.Review(
                rating = state.selectedRatingTypes.first().name,
                content = reviewTextFieldState.text.toString().takeIf { it.isNotBlank() },
                tags = state.selectedTagTypes.map { it.name }.takeIf { it.isNotEmpty() }
            )
        } else null
        
        val request = PostGameSubmissionRequest(
            winnerUserId = winner.userId,
            loserUserId = loser.userId,
            winnerScore = if (winner.userId == state.submitter.userId) state.submitterScore else state.receiverScore,
            loserScore = if (loser.userId == state.submitter.userId) state.submitterScore else state.receiverScore,
            review = review,
        )
        
        gameRepository.postGameSubmission(
            gameId = gameId,
            request = request,
        ).onSuccess { reviewId ->
            _uiState.update { it.copy(submitUiState = SubmitContract.SubmitUiState.Success) }
            Timber.tag(TAG).d("경기 제출 성공 - reviewId: $reviewId")
            _sideEffect.emit(SideEffect.NavigateBack)
        }.onFailure { throwable ->
            val errorMessage = throwable.message ?: "경기 제출 실패"
            _uiState.update { 
                it.copy(submitUiState = SubmitContract.SubmitUiState.Failure(errorMessage)) 
            }
            Timber.tag(TAG).e("경기 제출 실패: $errorMessage")
            _sideEffect.emit(SideEffect.ShowError(errorMessage))
        }
    }


    private fun getDummyState(): SubmitContract.State {
        return SubmitContract.State(
            submitter = MatchPlayer(userId = "1", name = "밤이달이"),
            receiver = MatchPlayer(userId = "2", name = "와쿠와쿠"),
            submitterScore = 0,
            receiverScore = 0,
            winner = null,
            loser = null,
            isButtonEnabled = false,
        )
    }

    companion object {
        private const val TAG = "SubmitViewModel"
    }
}
