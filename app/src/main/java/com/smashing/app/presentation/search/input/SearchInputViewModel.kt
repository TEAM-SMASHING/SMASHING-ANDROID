package com.smashing.app.presentation.search.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.common.state.UiState
import com.smashing.app.domain.model.Region
import com.smashing.app.domain.usecase.GetSeoulFilterRegionUseCase
import com.smashing.app.presentation.region.RegionContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
