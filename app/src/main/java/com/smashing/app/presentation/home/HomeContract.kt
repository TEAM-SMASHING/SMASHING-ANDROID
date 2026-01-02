package com.smashing.app.presentation.home

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.state.UiState
import com.smashing.app.data.model.DummyUser
import kotlinx.collections.immutable.ImmutableList

interface HomeContract {
    @Immutable
    data class State(
        val dummyUsersLoadState: UiState<ImmutableList<DummyUser>> = UiState.Idle,
    )

}
