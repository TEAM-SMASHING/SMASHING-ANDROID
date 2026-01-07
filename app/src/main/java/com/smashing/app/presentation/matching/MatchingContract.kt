package com.smashing.app.presentation.matching

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.state.UiState
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface MatchingContract {
    @Immutable
    data class State(
        val uiState: UiState<Unit> = UiState.Idle,
        val receivedMatchingList: ImmutableList<ReceivedMatchingItem> = persistentListOf(),
    )

}
