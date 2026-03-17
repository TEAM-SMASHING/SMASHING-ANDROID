package com.smashing.app.presentation.search.input

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.search.SuggestionItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchInputContract {
    @Immutable
    data class State(
        val searchNickNameUsersUiState: SearchInputUiState = SearchInputUiState.Idle,
        val suggestions: ImmutableList<SuggestionItemModel> = persistentListOf(),
        val isSuggestionVisible: Boolean = false,
    )
}

sealed interface SearchInputUiState {
    data object Idle : SearchInputUiState

    data object Loading : SearchInputUiState

    data object Empty : SearchInputUiState

    data object Success : SearchInputUiState

    data class Failure(
        val msg: String,
    ) : SearchInputUiState
}
