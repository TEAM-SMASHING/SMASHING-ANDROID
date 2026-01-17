package com.smashing.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.SearchRepository
import com.smashing.app.presentation.search.SearchContract.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    val searchInputState = TextFieldState()

    val searchInput = TextFieldState()

    init {
        fetchRegionUsersList(isRefresh = true)
        updateSearchInputText()
    }

    @OptIn(FlowPreview::class)
    fun updateSearchInputText() = viewModelScope.launch {
        snapshotFlow { searchInputState.text }
            .collect { searchInputText ->
                val text = searchInputState.toString()

                if (text.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            suggestions = persistentListOf()
                        )
                    }
                } else {
                    // Todo: 검색 api 호출
                    _uiState.update {
                        it.copy(
                            // suggestions = api 응답값
                        )
                    }
                }
            }
    }

    fun updateSelectedRegion(region: String) {
        _uiState.update {
            it.copy(selectedRegion = region)
        }
    }

    fun openTierBottomSheet() =
        _uiState.update {
            it.copy(isTierBottomSheetEnabled = true)
        }

    fun closeTierBottomSheet() =
        _uiState.update {
            it.copy(isTierBottomSheetEnabled = false)
        }

    fun updateSelectedTierItem(tierItem: String?) =
        _uiState.update {
            it.copy(selectedTierItem = tierItem)
        }

    fun applyTierItem() {
        updateCurrentTierText(_uiState.value.selectedTierItem)
        closeTierBottomSheet()
    }

    fun clearFilterTier() {
        updateCurrentTierText(null)
        updateSelectedTierItem(null)
    }

    fun updateCurrentTierText(tierText: String?) =
        _uiState.update {
            it.copy(
                currentTierText = tierText,
            )
        }

    fun openGenderBottomSheet() =
        _uiState.update {
            it.copy(isGenderBottomSheetEnabled = true)
        }

    fun closeGenderBottomSheet() =
        _uiState.update {
            it.copy(isGenderBottomSheetEnabled = false)
        }

    fun updateSelectedGenderItem(genderItem: String?) =
        _uiState.update {
            it.copy(selectedGenderItem = genderItem)
        }

    fun updateCurrentGenderText(genderText: String?) =
        _uiState.update {
            it.copy(
                currentGenderText = genderText,
            )
        }

    fun applyGenderItem() {
        updateCurrentGenderText(_uiState.value.selectedGenderItem)
        closeGenderBottomSheet()
    }

    fun clearFilterGender() {
        updateCurrentGenderText(null)
        updateSelectedGenderItem(null)
    }

    private fun fetchRegionUsersList(isRefresh: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.searchRegionUsersUiState == SearchUiState.Loading) return@launch
            if (!currentState.searchRegionUsersCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(searchRegionUsersUiState = SearchUiState.Loading) }

        searchRepository.getRegionUsersSearch(
            cursor = if (isRefresh) null else currentState.searchRegionUsersCursor.nextCursor,
            size = CURSOR_SIZE,
            gender = _uiState.value.selectedTierItem,
            tier = _uiState.value.selectedTierItem,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                state.copy(
                    searchList = if (isRefresh) {
                        cursorPage.items.toImmutableList()
                    } else {
                        (state.searchList + cursorPage.items).toImmutableList()
                    },
                    searchRegionUsersCursor = cursorPage.cursor,
                    searchRegionUsersUiState = if (cursorPage.items.isEmpty() && isRefresh) {
                        SearchUiState.Empty
                    } else {
                        SearchUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    searchRegionUsersUiState = SearchUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    companion object {
        private const val CURSOR_SIZE = 4
    }
}
