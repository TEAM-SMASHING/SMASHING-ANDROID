package com.smashing.app.presentation.search.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SearchInputViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchInputContract.State())
    val uiState = _uiState.asStateFlow()

    private val _searchInput = TextFieldState("")
    val searchInput: TextFieldState get() = _searchInput

    var searchResults: List<String> by mutableStateOf(emptyList())
        private set

    suspend fun run() {
        snapshotFlow { searchInput.text }
            .collectLatest { inputText ->
                searchResults = performSearch(inputText = inputText)
            }
    }

    fun clearQuery() {
        searchInput.setTextAndPlaceCursorAtEnd("")
    }

    private suspend fun performSearch(inputText: CharSequence): List<String> {
        TODO()
    }


}
