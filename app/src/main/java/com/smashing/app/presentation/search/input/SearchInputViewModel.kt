package com.smashing.app.presentation.search.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchInputViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchInputContract.State())
    val uiState = _uiState.asStateFlow()

    val searchInputState = TextFieldState()

    init {
        observeSearchInput()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchInput() = viewModelScope.launch {
        snapshotFlow { searchInputState.text }
            .debounce(SEARCH_NETWORK_DEBOUNCE)
            .collectLatest { searchInputText ->
                if (searchInputText.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            suggestions = persistentListOf(),
                            searchNickNameUsersUiState = SearchInputUiState.Idle,
                        )
                    }
                } else {
                    fetchNickNameUsersList(searchInputText.toString())
                }
            }
    }

    fun clearSearchInput() {
        searchInputState.clearText()
        _uiState.update {
            it.copy(
                suggestions = persistentListOf(),
                searchNickNameUsersUiState = SearchInputUiState.Idle,
            )
        }
    }

    private suspend fun fetchNickNameUsersList(nickname: String) {

        _uiState.update { it.copy(searchNickNameUsersUiState = SearchInputUiState.Loading) }

        searchRepository.getNickNameUsersSearch(nickname = nickname)
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        suggestions = result.toImmutableList(),
                        searchNickNameUsersUiState = SearchInputUiState.Success,
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        searchNickNameUsersUiState = SearchInputUiState.Failure(
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            }
    }

    companion object {
        private const val SEARCH_NETWORK_DEBOUNCE = 500L
    }
}
