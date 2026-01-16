package com.smashing.app.presentation.region

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.state.UiState
import com.smashing.app.domain.model.Region
import kotlinx.collections.immutable.ImmutableList

interface RegionContract {
    @Immutable
    data class State(
        val searchQuery: String = "",
        val selectedRegion: Region? = null,
        val regionLoadState: UiState<ImmutableList<Region>> = UiState.Idle,
    )

    sealed interface SideEffect {
        data object NavigateUp : SideEffect

        data class NavigateToRegionChange(
            val addressName: String,
            val cityName: String,
            val districtName: String,
        ) : SideEffect
    }
}