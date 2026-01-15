package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import com.kakao.sdk.user.model.Gender
import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.TierType
import com.smashing.app.presentation.search.style.FilterStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchContract {
    @Immutable
    data class State(
        val searchList: ImmutableList<SearchItemModel> = persistentListOf(
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
                userId = "search_userId_2",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.BRONZE_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_3",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.MALE,
                tierId = TierType.BRONZE_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_4",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.MALE,
                tierId = TierType.CHALLENGER,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_5",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_6",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_7",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.MALE,
                tierId = TierType.CHALLENGER,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_8",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_9",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.SILVER_2,
                wins = 254,
                losses = 38,
                reviews = 32,
            ),
            SearchItemModel(
                userId = "search_userId_10",
                nickname = "하나둘셋넷다여칠팔구",
                gender = GenderType.FEMALE,
                tierId = TierType.GOLD_1,
                wins = 254,
                losses = 38,
                reviews = 32,
            )
        ),
        val isTierBottomSheetEnabled: Boolean = false,
        val isGenderBottomSheetEnabled: Boolean = false,
        val currentTierText: String? = null,
        val currentGenderText: String? = null,
        val selectedTierItem: String? = null,
        val selectedGenderItem: String? = null,
    )
}
