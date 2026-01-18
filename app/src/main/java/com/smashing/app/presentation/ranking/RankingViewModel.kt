package com.smashing.app.presentation.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.repository.api.RankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankingRepository: RankingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RankingContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchRankingList()
    }

    fun fetchRankingList() = viewModelScope.launch {
        updateRankingUiState(RankingUiState.Loading)

        rankingRepository.getRankingList()
            .onSuccess { rankingData ->
                val userRankList = rankingData.topUsers
                _uiState.update { currentState ->
                    currentState.copy(
                        rankingUiState = RankingUiState.Success,
                        totalRankingList = userRankList.toImmutableList(),
                        topRankingList = userRankList.take(3).toImmutableList(),
                        restRankingList = userRankList.drop(3).toImmutableList(),
                        userInfo = rankingData.myRank?.let { user ->
                            UserRank(
                                userId = "",
                                nickname = user.nickname,
                                rank = 0,
                                tier = user.tierType,
                                lp = user.lp,
                            )
                        },
                    )
                }
            }
            .onFailure { throwable ->
                updateRankingUiState(
                    RankingUiState.Failure(throwable.message ?: "Unknown error")
                )
            }
    }


//    private fun createDummyRankingData(): ImmutableList<UserRank> {
//        return listOf(
//            UserRank(
//                userId = "user1",
//                nickname = "1위 유저",
//                rank = 1,
//                tier = TierType.CHALLENGER,
//                lp = 2500,
//            ),
//            UserRank(
//                userId = "user2",
//                nickname = "열글자테스트중입니다",
//                rank = 2,
//                tier = TierType.CHALLENGER,
//                lp = 2450,
//            ),
//            UserRank(
//                userId = "user3",
//                nickname = "1위 유저",
//                rank = 3,
//                tier = TierType.CHALLENGER,
//                lp = 2400,
//            ),
//            UserRank(
//                userId = "user4",
//                nickname = "프로게이머",
//                rank = 4,
//                tier = TierType.DIAMOND_1,
//                lp = 2350,
//            ),
//            UserRank(
//                userId = "user5",
//                nickname = "랭커킹커",
//                rank = 5,
//                tier = TierType.DIAMOND_1,
//                lp = 2300,
//            ),
//            UserRank(
//                userId = "user6",
//                nickname = "승리만추구",
//                rank = 6,
//                tier = TierType.DIAMOND_2,
//                lp = 2250,
//            ),
//            UserRank(
//                userId = "user7",
//                nickname = "플래티넘마스터",
//                rank = 7,
//                tier = TierType.DIAMOND_2,
//                lp = 2200,
//            ),
//            UserRank(
//                userId = "user8",
//                nickname = "골드라이더",
//                rank = 8,
//                tier = TierType.DIAMOND_3,
//                lp = 2150,
//            ),
//            UserRank(
//                userId = "user9",
//                nickname = "실버도전자",
//                rank = 9,
//                tier = TierType.PLATINUM_1,
//                lp = 2100,
//            ),
//            UserRank(
//                userId = "user10",
//                nickname = "브론즈탈출",
//                rank = 10,
//                tier = TierType.PLATINUM_2,
//                lp = 2050,
//            ),
//        ).toImmutableList()
//    }

    private fun updateRankingUiState(state: RankingUiState) = _uiState.update { currentState ->
        currentState.copy(
            rankingUiState = state
        )
    }
}