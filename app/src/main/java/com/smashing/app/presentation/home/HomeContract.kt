package com.smashing.app.presentation.home

import androidx.compose.runtime.Immutable
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.data.model.my.ActiveUserProfile
import com.smashing.app.data.model.my.UserProfileItem
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.presentation.home.type.DummyMatchedUser
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface HomeContract {
    @Immutable
    data class State(
        val loadState: HomeUiState = HomeUiState.Idle,
        val activeUserProfile: ActiveUserProfile? = null,
        val allUserProfiles: ImmutableList<UserProfileItem> = persistentListOf(),
        val topRankerList: ImmutableList<UserRank> = persistentListOf(),
        val regionRankerList: ImmutableList<UserRank> = persistentListOf(),
        val recommendedUserList: ImmutableList<MatchingCardState.Search> = persistentListOf(),
        val matchedUser: DummyMatchedUser? = null,
        val isNotice: Boolean = false,
    )

    sealed interface SideEffect {
        data object NavigateUp : SideEffect
        data object NavigateToNotice : SideEffect
        data object NavigateToRegionChange : SideEffect
    }
}

sealed interface HomeUiState {
    object Idle : HomeUiState

    object Loading : HomeUiState

    object Success : HomeUiState

    data class Failure(
        val msg: String,
    ) : HomeUiState
}