package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.model.search.SearchMainItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchContract {
    @Immutable
    data class State(
        val selectedRegion: String = "강서구",
        val regionItems: ImmutableList<String> = persistentListOf("양천구", "강서구", "장신구"),
        val searchList: ImmutableList<SearchMainItemModel> = persistentListOf(),
        val isTierBottomSheetEnabled: Boolean = false,
        val isGenderBottomSheetEnabled: Boolean = false,
        val tierBottomSheetList: ImmutableList<String> = persistentListOf(
            "아이언",
            "브론즈",
            "실버",
            "골드",
            "플래티넘",
            "다이아",
            "챌린저",
        ),
        val genderBottomSheetList: ImmutableList<String> = persistentListOf(
            "남성",
            "여성",
            "남여 모두",
        ),
        val currentTierText: String? = null,
        val currentGenderText: String? = null,
        val selectedTierItem: String? = null,
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
