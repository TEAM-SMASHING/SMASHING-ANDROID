package com.smashing.app.presentation.write.submit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.model.game.GameSubmission
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

    private val _uiState = MutableStateFlow(SubmitContract.State())
    val uiState = _uiState.asStateFlow()

    val leftTextFieldState: TextFieldState = TextFieldState()
    val rightTextFieldState: TextFieldState = TextFieldState()
    val reviewTextFieldState: TextFieldState = TextFieldState()

    init {
        initUserInfo()
        observeInputChanges()
    }

    private fun observeInputChanges() = viewModelScope.launch {
        launch {
            snapshotFlow { leftTextFieldState.text }.collect { text ->
                val score = text.toString().toIntOrNull() ?: 0
                updateSubmitterScore(score)
            }
        }
        launch {
            snapshotFlow { rightTextFieldState.text }.collect { text ->
                val score = text.toString().toIntOrNull() ?: 0
                updateReceiverScore(score)
            }
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

    fun updateSubmitterScore(score: Int) = _uiState.update { state ->
        val updatedSubmitter = state.submitter.copy(score = score)
        val updatedState = state.copy(submitter = updatedSubmitter)

        updatedState.copy(isButtonEnabled = validateButtonEnabled(updatedState))
    }

    fun updateReceiverScore(score: Int) = _uiState.update { state ->
        val updatedReceiver = state.receiver.copy(score = score)
        val updatedState = state.copy(receiver = updatedReceiver)

        updatedState.copy(isButtonEnabled = validateButtonEnabled(updatedState))
    }

    fun updateSelectedWinner(winnerName: String) = _uiState.update { state ->
        val winnerId =
            if (winnerName == state.submitter.name) state.submitter.userId else state.receiver.userId
        val updatedState = state.copy(winnerId = winnerId)
        updatedState.copy(isButtonEnabled = validateButtonEnabled(updatedState))
    }

    private fun validateButtonEnabled(state: SubmitContract.State): Boolean {
        if (leftTextFieldState.text.isEmpty() || rightTextFieldState.text.isEmpty()) {
            return false
        }

        val winnerId = state.winnerId ?: return false

        val isSubmitterWinner = winnerId == state.submitter.userId
        return isScoreMatchingWinner(
            isSubmitterWinner = isSubmitterWinner,
            submitterScore = state.submitter.score,
            receiverScore = state.receiver.score,
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
        val winnerId = state.winnerId ?: return@launch

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
        ).onSuccess {
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Success,
                    isResubmitDialogVisible = false
                )
            }
            _sideEffect.emit(SideEffect.NavigateToMatching)
        }.onFailure {
            _uiState.update {
                it.copy(
                    submitUiState = SubmitContract.SubmitUiState.Failure("경기 결과 제출 실패"),
                    isResubmitDialogVisible = false
                )
            }
        }
    }

    companion object {
        private const val TAG = "SubmitViewModel"
    }
}
