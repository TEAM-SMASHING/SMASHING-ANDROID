package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.repository.api.GameRepository
import com.smashing.app.data.repository.api.UserRepository
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
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val submitRoute = savedStateHandle.toRoute<Submit>()
    private val gameId = submitRoute.gameId
    private val opponentUserId = submitRoute.opponentUserId
    private val opponentNickname = submitRoute.opponentNickname
    val isFirstAttempt = submitRoute.isFirstAttempt
    
    private val _uiState = MutableStateFlow(SubmitContract.State())
    val uiState = _uiState.asStateFlow()
    
    init {
        initUserInfo()
    }
    
    private fun initUserInfo() = viewModelScope.launch {
        val currentUserId = userRepository.getUserId() ?: ""
        val currentUserNickname = userRepository.getUserNickname() ?: ""
        
        _uiState.update { state ->
            state.copy(
                submitter = MatchPlayer(userId = currentUserId, name = currentUserNickname),
                receiver = MatchPlayer(userId = opponentUserId, name = opponentNickname),
            )
        }
    }

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
        state.copy(
            selectedRating = if (state.selectedRating == type) null else type
        )
    }

    fun updateSelectedTagType(type: ReviewTagType) = _uiState.update { state ->
        val updatedTags = if (type in state.selectedTagList) {
            state.selectedTagList - type
        } else {
            state.selectedTagList + type
        }
        state.copy(selectedTagList = updatedTags.toImmutableSet())
    }

    fun showResubmitDialog() = _uiState.update { it.copy(isResubmitDialogVisible = true) }
    
    fun hideResubmitDialog() = _uiState.update { it.copy(isResubmitDialogVisible = false) }

    fun submitGame() = viewModelScope.launch {
        val state = _uiState.value
        val winner = state.winner
        val loser = state.loser

        if (winner == null || loser == null) return@launch

        _uiState.update { it.copy(submitUiState = SubmitContract.SubmitUiState.Loading) }

        val review = if (!isFirstAttempt) null else
            state.selectedRating?.let { rating ->
                PostGameSubmissionRequest.Review(
                    rating = rating.name,
                    content = reviewTextFieldState.text.toString().takeIf { it.isNotBlank() },
                    tags = state.selectedTagList.map { it.name }.takeIf { it.isNotEmpty() }
                )
            }

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
            _uiState.update { 
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Success,
                    isResubmitDialogVisible = false
                )
            }
            _sideEffect.emit(SideEffect.NavigateToMatching)
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Failure("${throwable.message}"),
                    isResubmitDialogVisible = false
                )
            }
        }
    }

    companion object {
        private const val TAG = "SubmitViewModel"
    }
}
