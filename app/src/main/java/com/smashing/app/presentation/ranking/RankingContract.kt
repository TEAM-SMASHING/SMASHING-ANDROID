package com.smashing.app.presentation.ranking

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.rank.UserRank
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface RankingContract {
    @Immutable
    data class State(
        val rankingUiState: RankingUiState = RankingUiState.Idle,
        val rankingList: ImmutableList<UserRank> = persistentListOf(),
        val userInfo: UserRank? = null,
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