package com.smashing.app.presentation.matching

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.presentation.matching.type.MatchingType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface MatchingContract {
    @Immutable
    data class State(
        val selectedType: MatchingType = MatchingType.RECEIVE,
        val receivedList: ImmutableList<ReceivedMatching> = persistentListOf(),
        val receivedCursor: Cursor = Cursor(),
        val receivedUiState: MatchingUiState = MatchingUiState.Idle,
        val sentList: ImmutableList<SentMatching> = persistentListOf(),
        val sentCursor: Cursor = Cursor(),
        val sentUiState: MatchingUiState = MatchingUiState.Idle,
        val acceptedList: ImmutableList<AcceptedMatching> = persistentListOf(),
        val acceptedCursor: Cursor = Cursor(),
        val acceptedUiState: MatchingUiState = MatchingUiState.Idle,
        val isDialogVisible: Boolean = false,
        val selectedMatchingId: String? = null,
        val selectedGameId: String? = null,
    )

    sealed interface SideEffect {
        data class NavigateToSubmit(
            val gameId: String,
            val opponentUserId: String,
            val opponentNickname: String,
            val isFirstAttempt: Boolean,
        ) : SideEffect
        data class NavigateToConfirm(
            val submissionId: String,
            val gameId: String,
            val isFirstAttempt: Boolean,
        ) : SideEffect
    }
}

sealed interface MatchingUiState {
    data object Idle : MatchingUiState

    data object Loading : MatchingUiState

    data object Empty : MatchingUiState

    data object Success : MatchingUiState

    data class Failure(
        val msg: String,
    ) : MatchingUiState
}
