package com.smashing.app.presentation.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.type.TierType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RankingContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchRankingData()
    }

    fun fetchRankingData() = viewModelScope.launch {
        updateRankingUiState(RankingUiState.Loading)

        val rankingData = createDummyRankingData()
        updateRankingUiState(RankingUiState.Success)
        _uiState.update { currentState ->
            currentState.copy(
                totalRankingList = rankingData,
                topRankingList = rankingData.take(3).toImmutableList(),
                restRankingList = rankingData.drop(3).toImmutableList(),
            )
        }
    }

    private fun createDummyRankingData(): ImmutableList<UserRank> {
        return listOf(
            UserRank(
                userId = "user1",
                nickname = "1위 유저",
                rank = 1,
                tierType = TierType.CHALLENGER,
                lp = 2500,
            ),
            UserRank(
                userId = "user2",
                nickname = "열글자테스트중입니다",
                rank = 2,
                tierType = TierType.CHALLENGER,
                lp = 2450,
            ),
            UserRank(
                userId = "user3",
                nickname = "1위 유저",
                rank = 3,
                tierType = TierType.CHALLENGER,
                lp = 2400,
            ),
            UserRank(
                userId = "user4",
                nickname = "프로게이머",
                rank = 4,
                tierType = TierType.DIAMOND_1,
                lp = 2350,
            ),
            UserRank(
                userId = "user5",
                nickname = "랭커킹커",
                rank = 5,
                tierType = TierType.DIAMOND_1,
                lp = 2300,
            ),
            UserRank(
                userId = "user6",
                nickname = "승리만추구",
                rank = 6,
                tierType = TierType.DIAMOND_2,
                lp = 2250,
            ),
            UserRank(
                userId = "user7",
                nickname = "플래티넘마스터",
                rank = 7,
                tierType = TierType.DIAMOND_2,
                lp = 2200,
            ),
            UserRank(
                userId = "user8",
                nickname = "골드라이더",
                rank = 8,
                tierType = TierType.DIAMOND_3,
                lp = 2150,
            ),
            UserRank(
                userId = "user9",
                nickname = "실버도전자",
                rank = 9,
                tierType = TierType.PLATINUM_1,
                lp = 2100,
            ),
            UserRank(
                userId = "user10",
                nickname = "브론즈탈출",
                rank = 10,
                tierType = TierType.PLATINUM_2,
                lp = 2050,
            ),
        ).toImmutableList()
    }

    private fun updateRankingUiState(state: RankingUiState) = _uiState.update { currentState ->
        currentState.copy(
            rankingUiState = state
        )
    }
}