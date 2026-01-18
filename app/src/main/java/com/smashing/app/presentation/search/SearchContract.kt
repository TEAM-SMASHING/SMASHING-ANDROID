package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.search.SuggestionItemModel
import com.smashing.app.data.model.search.SearchMainItemModel
import com.smashing.app.presentation.search.searchmain.style.GenderInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

interface SearchContract {
    @Immutable
    data class State(
        val selectedRegion: String = "강서구",  // Todo: 디폴트 값 제거
        val regionItems: ImmutableList<String> = persistentListOf("양천구", "강서구", "장신구"),
        val searchList: ImmutableList<SearchMainItemModel> = persistentListOf(),
        val isTierBottomSheetEnabled: Boolean = false,
        val isGenderBottomSheetEnabled: Boolean = false,
        val tierBottomSheetList: ImmutableList<String> = TierInfoStyle.entries.map { it.tierKName }.toImmutableList(),
        val genderBottomSheetList: ImmutableList<String> = GenderInfo.entries.map { it.genderKName }.toImmutableList(),
        val currentTierText: String? = null,
        val currentGenderText: String? = null,
        val selectedTierItem: TierInfoStyle? = null,
        val selectedGenderItem: GenderInfo? = null,
        val suggestions: ImmutableList<SuggestionItemModel> = persistentListOf(),
        val isSuggestionVisible: Boolean = false,
        val searchRegionUsersUiState: SearchUiState = SearchUiState.Idle,
        val searchNickNameUsersUiState: SearchUiState = SearchUiState.Idle,
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
