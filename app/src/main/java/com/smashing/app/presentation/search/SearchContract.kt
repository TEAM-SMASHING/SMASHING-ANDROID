package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import com.smashing.app.presentation.search.input.SuggestionItem
import com.smashing.app.data.model.search.SearchMainItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchContract {
    @Immutable
    data class State(
        val selectedRegion: String = "양천구",
        val regionItems: ImmutableList<String> = persistentListOf("양천구", "강서구", "장신구"),
        val searchList: ImmutableList<SearchMainItemModel> = persistentListOf(),
        val isTierBottomSheetEnabled: Boolean = false,
        val isGenderBottomSheetEnabled: Boolean = false,
        val currentTierText: String? = null,
        val currentGenderText: String? = null,
        val selectedTierItem: String? = null,
        val selectedGenderItem: String? = null,
        val suggestions: ImmutableList<SuggestionItem> = persistentListOf(),
        val isSuggestionVisible: Boolean = false,
    )
}
