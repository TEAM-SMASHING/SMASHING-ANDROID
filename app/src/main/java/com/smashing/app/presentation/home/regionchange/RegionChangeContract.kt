package com.smashing.app.presentation.home.regionchange

import androidx.compose.runtime.Immutable
import com.smashing.app.domain.model.Region

interface RegionChangeContract {
    @Immutable
    data class State(
        val selectedRegion: Region? = null,
        val regionLoadState: RegionChangeUiState = RegionChangeUiState.Idle,
    )

    sealed interface SideEffect {
        data object NavigateUp : SideEffect
        data object NavigateToRegion : SideEffect
    }
}

sealed interface RegionChangeUiState {
    object Idle : RegionChangeUiState

    object Success : RegionChangeUiState

    data class Failure(
        val msg: String,
    ) : RegionChangeUiState
}
