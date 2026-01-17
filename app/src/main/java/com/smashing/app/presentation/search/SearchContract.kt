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
        val suggestions: ImmutableList<SuggestionItem> = persistentListOf(),
        val isSuggestionVisible: Boolean = false,
    )
}
