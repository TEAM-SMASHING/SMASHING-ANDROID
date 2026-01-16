package com.smashing.app.presentation.search.input

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchInputContract {
    @Immutable
    data class State(
        val suggestions: ImmutableList<SuggestionItem> = persistentListOf(),
        val isSuggestionVisible: Boolean = false,
    )
}
