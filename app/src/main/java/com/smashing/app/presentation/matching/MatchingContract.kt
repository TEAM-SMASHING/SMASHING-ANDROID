package com.smashing.app.presentation.matching

import androidx.compose.runtime.Immutable
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
        val receiveList: ImmutableList<ReceivedMatching> = persistentListOf(),
        val sendList: ImmutableList<SentMatching> = persistentListOf(),
        val acceptedList: ImmutableList<AcceptedMatching> = persistentListOf(),
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
