package com.smashing.app.presentation.ranking

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.rank.UserRank
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

interface RankingContract {
    @Immutable
    data class State(
        val rankingUiState: RankingUiState = RankingUiState.Idle,
        val rankingList: ImmutableList<UserRank> = emptyList<UserRank>().toImmutableList(),
    )
}

sealed interface RankingUiState{
    object Idle : RankingUiState

    object Loading : RankingUiState

    object Success : RankingUiState

    data class Failure(
        val msg: String,
    ) : RankingUiState
}