package com.smashing.app.presentation.home

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.profile.ActiveUserProfile
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.model.search.SearchMainItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface HomeContract {
    @Immutable
    data class State(
        val loadState: HomeUiState = HomeUiState.Idle,
        val activeUserProfile: ActiveUserProfile? = null,
        val allUserProfiles: ImmutableList<ProfileItem> = persistentListOf(),
        val topRankerList: ImmutableList<UserRank> = persistentListOf(),
        val regionRankerList: ImmutableList<UserRank> = persistentListOf(),
        val recommendedUserList: ImmutableList<SearchMainItemModel> = persistentListOf(),
        val matchedUser: AcceptedMatching? = null,
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
