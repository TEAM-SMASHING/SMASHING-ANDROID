package com.smashing.app.presentation.write.confirm

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.data.repository.api.GameRepository
import com.smashing.app.data.type.ConfirmDenyType
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.confirm.ConfirmContract.ConfirmUiState
import com.smashing.app.presentation.write.confirm.ConfirmContract.SideEffect.ConfirmResultSideEffect
import com.smashing.app.presentation.write.confirm.ConfirmContract.SideEffect.ConfirmReviewSideEffect
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
                submissionDetail.winner.profileId == submissionDetail.submitter.profileId

            val submitter = PlayerInfo(
                profileId = submissionDetail.submitter.userId,
                name = submissionDetail.submitter.nickname,
            )

            val receiver = PlayerInfo(
                profileId = if (isSubmitterWinner) submissionDetail.loser.profileId else submissionDetail.winner.profileId,
                name = if (isSubmitterWinner) submissionDetail.loser.nickname else submissionDetail.winner.nickname,
            )

            currentState.copy(
                submitter = submitter,
                receiver = receiver,
                winnerId = submissionDetail.winner.profileId,
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

        val submissionConfirm = SubmissionConfirm(
            rating = state.selectedRating ?: return@launch,
            content = reviewTextFieldState.text.toString().takeIf { it.isNotBlank() },
            tags = state.selectedTagList.map { it.name }.takeIf { it.isNotEmpty() },
        )

        _uiState.update {
            it.copy(
                confirmUiState = ConfirmUiState.Loading,
                showConfirmDialog = false,
            )
        }

        gameRepository.postConfirmSubmission(
            gameId = gameId,
            submissionId = submissionId,
            submissionConfirm = submissionConfirm,
        ).onSuccess { reviewId ->
            updateConfirmUiState(uiState = ConfirmUiState.Success)
            _sideEffect.emit(ConfirmReviewSideEffect.NavigateToConfirmReview(reviewId))
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

    fun showDenyBottomSheet() = _uiState.update {
        it.copy(showDenyBottomSheet = true)
    }

    fun hideDenyBottomSheet() = _uiState.update {
        it.copy(showDenyBottomSheet = false)
    }

    fun showRejectDialog() = _uiState.update {
        it.copy(showRejectDialog = true)
    }

    fun hideRejectDialog() = _uiState.update {
        it.copy(showRejectDialog = false)
    }

    fun showConfirmDialog() = _uiState.update {
        it.copy(showConfirmDialog = true)
    }

    fun hideConfirmDialog() = _uiState.update {
        it.copy(showConfirmDialog = false)
    }

    fun updateSelectedDenyReason(reason: ConfirmDenyType) = _uiState.update {
        it.copy(selectedDenyReason = reason)
    }

    fun rejectSubmission() = viewModelScope.launch {
        val reason = _uiState.value.selectedDenyReason

        _uiState.update {
            it.copy(
                confirmUiState = ConfirmUiState.Loading,
                showDenyBottomSheet = false,
                showRejectDialog = false,
            )
        }

        gameRepository.postRejectSubmission(
            gameId = gameId,
            submissionId = submissionId,
            reason = reason,
        ).onSuccess {
            _uiState.update {
                it.copy(
                    confirmUiState = ConfirmUiState.Success,
                    selectedDenyReason = null,
                )
            }
            _sideEffect.emit(ConfirmResultSideEffect.NavigateBack)
        }.onFailure { throwable ->
            updateConfirmUiState(
                uiState = ConfirmUiState.Failure(
                    throwable.message ?: "Unknown error"
                )
            )
        }
    }
}
