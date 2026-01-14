package com.smashing.app.presentation.search

import androidx.lifecycle.ViewModel
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.TierType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        getDummyList()
    }

    // TODO 더미 데이터 삭제 예정
    private fun getDummyList(): SearchContract.State {
        val dummySearchList = persistentListOf(
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.BRONZE_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.MALE,
                tierId = TierType.BRONZE_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.MALE,
                tierId = TierType.CHALLENGER,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.MALE,
                tierId = TierType.CHALLENGER,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.SILVER_2,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_1",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            )
        )

        return SearchContract.State(
            searchList = dummySearchList,
        )
    }
}
