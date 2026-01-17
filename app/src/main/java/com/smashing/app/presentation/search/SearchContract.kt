package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.home.type.TierInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

interface SearchContract {
    @Immutable
    data class State(
        val selectedRegion: String = "강서구",
        val regionItems: ImmutableList<String> = persistentListOf("양천구", "강서구", "장신구"),
        val searchList: ImmutableList<SearchMainItemModel> = persistentListOf(),
        val isTierBottomSheetEnabled: Boolean = false,
        val isGenderBottomSheetEnabled: Boolean = false,
        val tierBottomSheetList: ImmutableList<String> = TierInfo.entries.map { it.tierKName }.toImmutableList(),
        val genderBottomSheetList: ImmutableList<String> = persistentListOf(
            "남성",
            "여성",
            "남여 모두",
        ),
        val currentTierText: String? = null,
        val currentGenderText: String? = null,
        val selectedTierItem: TierInfo? = null,
        val selectedGenderItem: String? = null,
        val suggestions: ImmutableList<SuggestionItemModel> = persistentListOf(),
        val isSuggestionVisible: Boolean = false,
        val searchRegionUsersUiState: SearchUiState = SearchUiState.Idle,
        val searchRegionUsersCursor: Cursor = Cursor(),
    )

    sealed interface SearchUiState {
        data object Idle : SearchUiState

        data object Loading : SearchUiState

        data object Empty : SearchUiState

        data object Success : SearchUiState

        data class Failure(
            val msg: String,
        ) : SearchUiState
    }
}
