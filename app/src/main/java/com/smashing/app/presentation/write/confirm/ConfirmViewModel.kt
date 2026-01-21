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
import com.smashing.app.presentation.write.model.PlayerInfo
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

    val isFirstAttempt = confirmRoute.isFirstAttempt

    private val _uiState = MutableStateFlow(ConfirmContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ConfirmContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val reviewTextFieldState: TextFieldState = TextFieldState()
    val leftTextFieldState: TextFieldState = TextFieldState()
    val rightTextFieldState: TextFieldState = TextFieldState()

    init {
        fetchGameSubmission()
    }

    private fun fetchGameSubmission() = viewModelScope.launch {
        _uiState.update { it.copy(confirmUiState = ConfirmUiState.Loading) }

        gameRepository.getGameSubmission(
            gameId = gameId,
            submissionId = submissionId,
        ).onSuccess { submissionDetail ->
            updateGameSubmission(submissionDetail)

            val currentState = _uiState.value
            leftTextFieldState.edit {
                replace(0, length, currentState.submitter.score.toString())
            }
            rightTextFieldState.edit {
                replace(0, length, currentState.receiver.score.toString())
            }
        }.onFailure { throwable ->
            updateConfirmUiState(
                uiState = ConfirmUiState.Failure(
                    throwable.message ?: "Unknown error"
                )
            )
        }
    }

    private fun updateGameSubmission(submissionDetail: GameSubmissionDetail) {
        _uiState.update { currentState ->
            val isSubmitterWinner =
                submissionDetail.winner.userId == submissionDetail.submitter.userId

            val submitter = PlayerInfo(
                userId = submissionDetail.submitter.userId,
                name = submissionDetail.submitter.nickname,
                score = if (isSubmitterWinner) submissionDetail.winner.score else submissionDetail.loser.score
            )

            val receiver = if (isSubmitterWinner) {
                PlayerInfo(
                    userId = submissionDetail.loser.userId,
                    name = submissionDetail.loser.nickname,
                    score = submissionDetail.loser.score
                )
            } else {
                PlayerInfo(
                    userId = submissionDetail.winner.userId,
                    name = submissionDetail.winner.nickname,
                    score = submissionDetail.winner.score
                )
            }

            currentState.copy(
                submitter = submitter,
                receiver = receiver,
                winnerId = submissionDetail.winner.userId,
                confirmUiState = ConfirmUiState.Idle,
            )
        }
    }

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
        ).onSuccess { reviewId ->
            updateConfirmUiState(uiState = ConfirmUiState.Success)
            _sideEffect.emit(ConfirmContract.SideEffect.NavigateToConfirmReview(reviewId))
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

    fun rejectSubmission(reason: String) = viewModelScope.launch {
        _uiState.update { it.copy(confirmUiState = ConfirmUiState.Loading) }

        gameRepository.postRejectSubmission(
            gameId = gameId,
            submissionId = submissionId,
            reason = reason,
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
}
