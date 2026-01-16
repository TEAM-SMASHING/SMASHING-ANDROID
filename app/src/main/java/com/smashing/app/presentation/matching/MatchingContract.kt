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
        val loadState: MatchingUiState = MatchingUiState.Idle,
        val selectedType: MatchingType = MatchingType.SEND,
        val receivedList: ImmutableList<ReceivedMatching> = persistentListOf(),
        val receivedCursor: Cursor = Cursor(),
        val sentList: ImmutableList<SentMatching> = persistentListOf(),
        val sentCursor: Cursor = Cursor(),
        val acceptedList: ImmutableList<AcceptedMatching> = persistentListOf(),
        val acceptedCursor: Cursor = Cursor(),
        val isDialogVisible: Boolean = false,
    )
}

sealed interface MatchingUiState {
    data object Idle : MatchingUiState

    data object Empty : MatchingUiState

    data object Success : MatchingUiState

    data class Failure(
        val msg: String,
    ) : MatchingUiState
}
