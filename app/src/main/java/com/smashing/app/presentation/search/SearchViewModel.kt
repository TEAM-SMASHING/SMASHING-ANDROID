package com.smashing.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.designsystem.style.TierInfoStyle
import com.smashing.app.data.repository.api.SearchRepository
import com.smashing.app.presentation.search.SearchContract.SearchUiState
import com.smashing.app.presentation.search.searchmain.style.GenderInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    val searchInputState = TextFieldState()

    init {
        fetchRegionUsersList(isRefresh = true)
        updateSearchInputText()
    }

    @OptIn(FlowPreview::class)
    fun updateSearchInputText() = viewModelScope.launch {
        snapshotFlow { searchInputState.text }
            .debounce(SEARCH_NETWORK_DEBOUNCE)
            .collectLatest { searchInputText ->
                if (searchInputText.isEmpty()) {
                    _uiState.update { it.copy(searchNickNameUsersUiState = SearchUiState.Empty) }
                } else {
                    fetchNickNameUsersList(searchInputText)
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
            it.copy(selectedTierItem = TierInfoStyle.findTierInfo(tierItem))
        }

    fun applyTierItem() {
        updateCurrentTierText(_uiState.value.selectedTierItem?.tierKName)
        fetchRegionUsersList(isRefresh = true)
        closeTierBottomSheet()
    }

    fun clearFilterTier() {
        updateCurrentTierText(null)
        updateSelectedTierItem(null)
        fetchRegionUsersList(isRefresh = true)
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
            it.copy(selectedGenderItem = GenderInfo.findGenderInfo(genderItem))
        }

    fun updateCurrentGenderText(genderText: String?) =
        _uiState.update {
            it.copy(
                currentGenderText = genderText,
            )
        }

    fun applyGenderItem() {
        updateCurrentGenderText(_uiState.value.selectedGenderItem?.genderKName)
        fetchRegionUsersList(isRefresh = true)
        closeGenderBottomSheet()
    }

    fun clearFilterGender() {
        updateCurrentGenderText(null)
        updateSelectedGenderItem(null)
        fetchRegionUsersList(isRefresh = true)
    }

    fun fetchNickNameUsersList(nickname: CharSequence) = viewModelScope.launch {

        _uiState.update { it.copy(searchNickNameUsersUiState = SearchUiState.Loading) }

        searchRepository.getNickNameUsersSearch(nickname = nickname.toString())
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        suggestions = result.toImmutableList(),
                        searchNickNameUsersUiState = SearchUiState.Success,
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        searchNickNameUsersUiState = SearchUiState.Failure(
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            }
    }

    fun fetchRegionUsersList(isRefresh: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.searchRegionUsersUiState == SearchUiState.Loading) return@launch
            if (!currentState.searchRegionUsersCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(searchRegionUsersUiState = SearchUiState.Loading) }

        searchRepository.getRegionUsersSearch(
            cursor = if (isRefresh) null else currentState.searchRegionUsersCursor.nextCursor,
            size = CURSOR_SIZE,
            gender = _uiState.value.selectedGenderItem?.genderName,
            tier = _uiState.value.selectedTierItem?.name,
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
        private const val CURSOR_SIZE = 20
        private const val SEARCH_NETWORK_DEBOUNCE = 500L
    }
}
