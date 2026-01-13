package com.smashing.app.presentation.profile

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.ProfileReview
import com.smashing.app.data.model.UserProfileInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface ProfileContract {
    @Immutable
    data class State(
        val loadState: ProfileUiState = ProfileUiState.Idle,
        val profileInfo: UserProfileInfo? = null,
        val reviews: ImmutableList<ProfileReview> = persistentListOf()
    )
}

sealed interface ProfileUiState {
    data object Idle : ProfileUiState
    data object Loading : ProfileUiState
    data object Success : ProfileUiState
    data class Failure(
        val msg: String,
    ) : ProfileUiState
}
