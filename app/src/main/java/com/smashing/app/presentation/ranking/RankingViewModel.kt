package com.smashing.app.presentation.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.repository.api.RankingRepository
import com.smashing.app.data.repository.api.UserRepository
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
    private val userRepository: UserRepository,
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
                val storedUserProfileId = userRepository.getUserProfileId()

                _uiState.update { currentState ->
                    currentState.copy(
                        rankingUiState = RankingUiState.Success,
                        totalRankingList = userRankList.toImmutableList(),
                        topRankingList = userRankList.take(3).toImmutableList(),
                        restRankingList = userRankList.drop(3).toImmutableList(),
                        userInfo = rankingData.myRank?.let { user ->
                            UserRank(
                                userProfileId = storedUserProfileId ?: "",
                                nickname = user.nickname,
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

    private fun updateRankingUiState(state: RankingUiState) = _uiState.update { currentState ->
        currentState.copy(
            rankingUiState = state
        )
    }
}
