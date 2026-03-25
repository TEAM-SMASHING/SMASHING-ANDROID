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

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        if (!isFirstAttempt) {
            fetchPreviousSubmission()
        } else {
            initUserInfo()
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
                    submitUiState = SubmitContract.SubmitUiState.Failure("이전 제출 결과 조회 실패")
                )
            }
        }
    }

    private fun updateFromSubmissionDetail(submissionDetail: GameSubmissionDetail) {
        val isSubmitterWinner =
            submissionDetail.winner.profileId == submissionDetail.submitter.userId

        val submitter = PlayerInfo(
            userId = submissionDetail.submitter.userId,
            name = submissionDetail.submitter.nickname,
        )

        val receiver = PlayerInfo(
            userId = if (isSubmitterWinner) submissionDetail.loser.profileId else submissionDetail.winner.profileId,
            name = if (isSubmitterWinner) submissionDetail.loser.nickname else submissionDetail.winner.nickname,
        )

        _uiState.update { state ->
            state.copy(
                submitter = submitter,
                receiver = receiver,
                winnerId = submissionDetail.winner.profileId,
                isButtonEnabled = true,
            )
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
                ),
                receiver = PlayerInfo(
                    userId = opponentUserId,
                    name = opponentNickname,
                ),
            )
        }
    }

    val reviewTextFieldState: TextFieldState = TextFieldState()

    fun updateSelectedWinner(winnerName: String) = _uiState.update { state ->
        val winnerId =
            if (winnerName == state.submitter.name) state.submitter.userId else state.receiver.userId

        state.copy(
            winnerId = winnerId,
            isButtonEnabled = true,
        )
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

    fun showResubmitDialog() = _uiState.update { it.copy(isResubmitDialogVisible = true) }

    fun hideResubmitDialog() = _uiState.update { it.copy(isResubmitDialogVisible = false) }

    fun showAlertDialog() = _uiState.update { it.copy(isAlertDialogOpen = true) }

    fun hideAlertDialog() = _uiState.update { it.copy(isAlertDialogOpen = false) }

    fun hideConfirmDialog() = _uiState.update { it.copy(isConfirmDialogOpen = false) }

    fun updateIsConfirmDialogOpen() = viewModelScope.launch {
        _uiState.update { it.copy(isConfirmDialogOpen = false) }
        _sideEffect.emit(SideEffect.NavigateToMatching)
    }

    fun submitGame() = viewModelScope.launch {
        val state = _uiState.value
        val winnerProfileId = state.winnerId ?: return@launch

        _uiState.update { it.copy(submitUiState = SubmitContract.SubmitUiState.Loading) }

        val isSubmitterWinner = winnerProfileId == state.submitter.userId

        val loserProfileId = if (isSubmitterWinner) state.receiver.userId else state.submitter.userId

        val review = if (!isFirstAttempt) null else
            state.selectedRating?.let { rating ->
                GameSubmission.Review(
                    rating = rating.name,
                    content = reviewTextFieldState.text.toString().takeIf { it.isNotBlank() },
                    tags = state.selectedTagList.map { it.name }.takeIf { it.isNotEmpty() }
                )
            }

        val gameSubmission = GameSubmission(
            winnerProfileId = winnerProfileId,
            loserProfileId = loserProfileId,
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
                    isAlertDialogOpen = false,
                    isConfirmDialogOpen = false,
                )
            }
            _sideEffect.emit(SideEffect.NavigateToMatching)
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Failure("경기 결과 제출 실패"),
                    isResubmitDialogVisible = false,
                    isAlertDialogOpen = false,
                    isConfirmDialogOpen = true,
                )
            }
        }
    }

}
