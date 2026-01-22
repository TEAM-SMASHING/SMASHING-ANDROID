package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.repository.api.GameRepository
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.model.PlayerInfo
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
    private val submissionId = submitRoute.submissionId

    private val _uiState = MutableStateFlow(SubmitContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        initUserInfo()
        if (!isFirstAttempt) {
            fetchPreviousSubmission()
        }
    }

    private fun fetchPreviousSubmission() = viewModelScope.launch {
        val currentSubmissionId = submissionId ?: return@launch

        gameRepository.getGameSubmission(
            gameId = gameId,
            submissionId = currentSubmissionId,
        ).onSuccess { submissionDetail ->
            updateFromSubmissionDetail(submissionDetail)
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Failure(
                        throwable.message ?: "이전 제출 결과 조회 실패"
                    )
                )
            }
        }
    }

    private fun updateFromSubmissionDetail(submissionDetail: GameSubmissionDetail) {
        val isSubmitterWinner =
            submissionDetail.winner.userId == submissionDetail.submitter.userId

        val submitter = PlayerInfo(
            userId = submissionDetail.submitter.userId,
            name = submissionDetail.submitter.nickname,
            score = if (isSubmitterWinner) submissionDetail.winner.score else submissionDetail.loser.score,
        )

        val receiver = PlayerInfo(
            userId = if (isSubmitterWinner) submissionDetail.loser.userId else submissionDetail.winner.userId,
            name = if (isSubmitterWinner) submissionDetail.loser.nickname else submissionDetail.winner.nickname,
            score = if (isSubmitterWinner) submissionDetail.loser.score else submissionDetail.winner.score,
        )

        _uiState.update { state ->
            state.copy(
                submitter = submitter,
                receiver = receiver,
                winnerId = submissionDetail.winner.userId,
                isButtonEnabled = isScoreMatchingWinner(
                    isSubmitterWinner = isSubmitterWinner,
                    submitterScore = submitter.score,
                    receiverScore = receiver.score,
                ),
            )
        }

        val currentState = _uiState.value
        leftTextFieldState.edit {
            replace(0, length, currentState.submitter.score.toString())
        }
        rightTextFieldState.edit {
            replace(0, length, currentState.receiver.score.toString())
        }
    }

    private fun initUserInfo() = viewModelScope.launch {
        val currentUserId = userRepository.getUserId() ?: ""
        val currentUserNickname = userRepository.getUserNickname() ?: ""

        _uiState.update { state ->
            state.copy(
                submitter = PlayerInfo(
                    userId = currentUserId,
                    name = currentUserNickname,
                    score = 0
                ),
                receiver = PlayerInfo(userId = opponentUserId, name = opponentNickname, score = 0),
            )
        }
    }

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val leftTextFieldState: TextFieldState = TextFieldState()
    val rightTextFieldState: TextFieldState = TextFieldState()
    val reviewTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedWinner(winnerName: String) = _uiState.update { state ->
        val winnerId =
            if (winnerName == state.submitter.name) state.submitter.userId else state.receiver.userId
        val isSubmitterWinner = winnerId == state.submitter.userId
        state.copy(
            winnerId = winnerId,
            isButtonEnabled = hasBothScoreInputs() &&
                    isScoreMatchingWinner(
                        isSubmitterWinner = isSubmitterWinner,
                        submitterScore = state.submitter.score,
                        receiverScore = state.receiver.score,
                    ),
        )
    }

    fun updateSubmitterScore(score: Int) = _uiState.update { state ->
        val updatedSubmitter = state.submitter.copy(score = score)
        val isSubmitterWinner = state.winnerId == updatedSubmitter.userId

        state.copy(
            submitter = updatedSubmitter,
            isButtonEnabled = state.winnerId != null &&
                    hasBothScoreInputs() &&
                    isScoreMatchingWinner(
                        isSubmitterWinner = isSubmitterWinner,
                        submitterScore = score,
                        receiverScore = state.receiver.score,
                    ),
        )
    }

    fun updateReceiverScore(score: Int) = _uiState.update { state ->
        val updatedReceiver = state.receiver.copy(score = score)
        val isSubmitterWinner = state.winnerId == state.submitter.userId

        state.copy(
            receiver = updatedReceiver,
            isButtonEnabled = state.winnerId != null &&
                    hasBothScoreInputs() &&
                    isScoreMatchingWinner(
                        isSubmitterWinner = isSubmitterWinner,
                        submitterScore = state.submitter.score,
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

    private fun hasBothScoreInputs(): Boolean =
        leftTextFieldState.text.isNotBlank() && rightTextFieldState.text.isNotBlank()

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

    fun hideConfirmDialog() = _uiState.update { it.copy(isConfirmDialogOpen = false) }

    fun updateIsConfirmDialogOpen() = viewModelScope.launch {
        _uiState.update { it.copy(isConfirmDialogOpen = false) }
        _sideEffect.emit(SideEffect.NavigateToMatching)
    }

    fun submitGame() = viewModelScope.launch {
        val state = _uiState.value
        val winnerId = state.winnerId

        if (winnerId == null) return@launch

        _uiState.update { it.copy(submitUiState = SubmitContract.SubmitUiState.Loading) }

        val isSubmitterWinner = winnerId == state.submitter.userId
        val (winnerScore, loserScore) = if (isSubmitterWinner) state.submitter.score to state.receiver.score
        else state.receiver.score to state.submitter.score

        val loserId = if (isSubmitterWinner) state.receiver.userId else state.submitter.userId

        val review = if (!isFirstAttempt) null else
            state.selectedRating?.let { rating ->
                GameSubmission.Review(
                    rating = rating.name,
                    content = reviewTextFieldState.text.toString().takeIf { it.isNotBlank() },
                    tags = state.selectedTagList.map { it.name }.takeIf { it.isNotEmpty() }
                )
            }

        val gameSubmission = GameSubmission(
            winnerUserId = winnerId,
            loserUserId = loserId,
            winnerScore = winnerScore,
            loserScore = loserScore,
            review = review,
        )

        gameRepository.postGameSubmission(
            gameId = gameId,
            gameSubmission = gameSubmission,
        ).onSuccess { reviewId ->
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Success,
                    isResubmitDialogVisible = false,
                    isConfirmDialogOpen = false,
                )
            }
            _sideEffect.emit(SideEffect.NavigateToMatching)
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Failure("경기 결과 제출 실패"),
                    isResubmitDialogVisible = false,
                    isConfirmDialogOpen = true,
                )
            }
        }
    }

}
