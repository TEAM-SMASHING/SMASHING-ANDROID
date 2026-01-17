package com.smashing.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.search.searchmain.SearchMainItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    val searchInputState = TextFieldState()

    val searchInput = TextFieldState()

    init {
        getDummyList()
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

    // TODO 더미 데이터 삭제 예정
    private fun getDummyList() {
        _uiState.update {
            it.copy(
                searchList =
                    persistentListOf(
                        SearchMainItemModel(
                            userId = "search_userId_1",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.GOLD_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_2",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.BRONZE_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_3",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.MALE,
                            tierId = TierType.BRONZE_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_4",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.MALE,
                            tierId = TierType.CHALLENGER,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_5",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.GOLD_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_6",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.GOLD_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_7",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.MALE,
                            tierId = TierType.CHALLENGER,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_8",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.GOLD_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_9",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.SILVER_2,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                        SearchMainItemModel(
                            userId = "search_userId_10",
                            nickname = "하나둘셋넷다여칠팔구",
                            gender = GenderType.FEMALE,
                            tierId = TierType.GOLD_1,
                            wins = 254,
                            losses = 38,
                            reviews = 32,
                        ),
                    )
            )
        }
    }
}
