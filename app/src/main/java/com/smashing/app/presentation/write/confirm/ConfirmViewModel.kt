package com.smashing.app.presentation.write.confirm

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.data.repository.api.GameRepository
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.confirm.ConfirmContract.ConfirmUiState
import com.smashing.app.presentation.write.model.MatchPlayer
import com.smashing.app.presentation.write.navigation.Confirm
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
class ConfirmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val confirmRoute = savedStateHandle.toRoute<Confirm>()
    private val submissionId = confirmRoute.submissionId
    private val gameId = confirmRoute.gameId
    private val opponentUserId = confirmRoute.opponentUserId
    private val opponentNickname = confirmRoute.opponentNickname
    val isFirstAttempt = confirmRoute.isFirstAttempt

    private val _uiState = MutableStateFlow(ConfirmContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        initUserInfo()
        fetchGameSubmission()
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

    private fun fetchGameSubmission() = viewModelScope.launch {
        _uiState.update { it.copy(confirmUiState = ConfirmUiState.Loading) }

        gameRepository.getGameSubmission(
            gameId = gameId,
            submissionId = submissionId,
        ).onSuccess { submissionDetail ->
            _uiState.update { state ->
                getGameSubmission(currentState = state, submissionDetail = submissionDetail)
            }
        }.onFailure { throwable ->
            updateConfirmUiState(
                uiState = ConfirmUiState.Failure(
                    throwable.message ?: "Unknown error"
                )
            )
        }
    }

    private fun getGameSubmission(
        currentState: ConfirmContract.State,
        submissionDetail: GameSubmissionDetail,
    ): ConfirmContract.State {
        val isSubmitterWinner = submissionDetail.winner.userId == currentState.submitter.userId

        val (winner, loser) = if (isSubmitterWinner) currentState.submitter to currentState.receiver
        else currentState.receiver to currentState.submitter

        val (submitterScore, receiverScore) = if (isSubmitterWinner) submissionDetail.winner.score to submissionDetail.loser.score
        else submissionDetail.loser.score to submissionDetail.winner.score

        return currentState.copy(
            submitterScore = submitterScore,
            receiverScore = receiverScore,
            winner = winner,
            loser = loser,
            confirmUiState = ConfirmUiState.Idle,
        )
    }

    private val _sideEffect = MutableSharedFlow<ConfirmContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val reviewTextFieldState: TextFieldState = TextFieldState()
    val leftTextFieldState: TextFieldState = TextFieldState()
    val rightTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedRatingType(type: ReviewRatingType) = _uiState.update { state ->
        state.copy(
            selectedRating = if (state.selectedRating == type) null else type
        )
    }


    private fun isScoreMatchingWinner(
        isSubmitterWinner: Boolean,
        submitterScore: Int,
        receiverScore: Int,
    ): Boolean = if (isSubmitterWinner) submitterScore > receiverScore
    else receiverScore > submitterScore


    fun updateSelectedTagType(type: ReviewTagType) = _uiState.update { state ->
        val updatedTags = if (type in state.selectedTagList) {
            state.selectedTagList - type
        } else {
            state.selectedTagList + type
        }
        state.copy(selectedTagList = updatedTags.toImmutableSet())
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

    fun confirmSubmission() = viewModelScope.launch {
        val state = _uiState.value

        _uiState.update { it.copy(confirmUiState = ConfirmUiState.Loading) }

        val submissionConfirm = SubmissionConfirm(
            rating = state.selectedRating?.name ?: return@launch,
            content = reviewTextFieldState.text.toString().takeIf { it.isNotBlank() },
            tags = state.selectedTagList.map { it.name }.takeIf { it.isNotEmpty() },
        )

        gameRepository.postConfirmSubmission(
            gameId = gameId,
            submissionId = submissionId,
            submissionConfirm = submissionConfirm,
        ).onSuccess {
            updateConfirmUiState(uiState = ConfirmUiState.Success)
            _sideEffect.emit(ConfirmContract.SideEffect.NavigateBack)
        }.onFailure { throwable ->
            updateConfirmUiState(
                uiState = ConfirmUiState.Failure(
                    throwable.message ?: "Unknown error"
                )
            )
        }
    }

    private fun updateConfirmUiState(uiState: ConfirmUiState) = _uiState.update {
        it.copy(confirmUiState = uiState)
    }

    fun getIsFirstAttempt(): Boolean = isFirstAttempt

    fun showResubmitDialog() = _uiState.update {
        it.copy(isResubmitDialogVisible = true)
    }

    fun hideResubmitDialog() = _uiState.update {
        it.copy(isResubmitDialogVisible = false)
    }

    fun denySubmission(reason: String) = viewModelScope.launch {
        hideResubmitDialog()
        
        // TODO: 반려 API 구현
        _sideEffect.emit(ConfirmContract.SideEffect.NavigateBack)
    }
}
