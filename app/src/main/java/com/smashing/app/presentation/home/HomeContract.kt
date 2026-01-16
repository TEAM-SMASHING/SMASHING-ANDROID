package com.smashing.app.presentation.home

import androidx.compose.runtime.Immutable
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.data.model.profile.ActiveUserProfile
import com.smashing.app.data.model.rank.TopUserInfo
import com.smashing.app.presentation.home.type.DummyMatchedUser
import com.smashing.app.presentation.home.type.TierInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface HomeContract {
    @Immutable
    data class State(
        val loadState: HomeUiState = HomeUiState.Idle,
        val activeUserProfile: ActiveUserProfile? = null,
        val topRankerList: ImmutableList<TopUserInfo> = persistentListOf(),
        val matchingCardList: ImmutableList<MatchingCardState.Search> = persistentListOf(),
        val matchedUser: DummyMatchedUser? = null,
        val selectedTierInfo: TierInfo = TierInfo.IRON,
        val isNotice: Boolean = false,
        )

    sealed interface SideEffect {
        data object NavigateUp : SideEffect
        data object NavigateToNotice : SideEffect
        data object NavigateToRegionChange : SideEffect
    }
}

sealed interface HomeUiState{
    object Idle : HomeUiState

    object Loading : HomeUiState

    object Success : HomeUiState

    data class Failure(
        val msg: String,
    ) : HomeUiState
}