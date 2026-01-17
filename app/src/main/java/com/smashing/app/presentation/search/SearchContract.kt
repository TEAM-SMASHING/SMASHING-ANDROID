package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchContract {
    @Immutable
    data class State(
        val selectedRegion: String = "양천구",
        val searchList: ImmutableList<SearchItemModel> = persistentListOf(),
        val isTierBottomSheetEnabled: Boolean = false,
        val isGenderBottomSheetEnabled: Boolean = false,
        val currentTierText: String? = null,
        val currentGenderText: String? = null,
        val selectedTierItem: String? = null,
        val selectedGenderItem: String? = null,
    )
}
