package com.smashing.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.RankingRepository
import com.smashing.app.data.repository.api.SearchRepository
import com.smashing.app.presentation.home.type.DummyMatchedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rankingRepository: RankingRepository,
    private val searchRepository: SearchRepository,
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    fun fetchMyTierProfile() = viewModelScope.launch {
        myRepository.getMyTierProfile()
            .onSuccess { userProfile ->
                _uiState.update { currentState ->
                    currentState.copy(
                        activeUserProfile = userProfile.activeUserProfile,
                        allUserProfiles = userProfile.allProfiles.toImmutableList(),
                    )
                }
            }
            .onFailure {throwable ->
                Timber.tag("HomeViewModel").e(throwable)
            }
    }

    fun fetchRecommendedUserList() = viewModelScope.launch {
        searchRepository.getRecommendedUsers()
            .onSuccess { recommendedUsers ->
                val matchingCardList = recommendedUsers.map { user ->
                    MatchingCardState.Search(
                        userId = user.userId,
                        nickname = user.nickname,
                        genderType = user.gender,
                        tierType = user.tierType,
                        //TODO userId 기반 프로필 이동
                        onProfileClick = {},
                        winCount = user.wins,
                        loseCount = user.losses,
                        reviewCount = user.reviews,
                    )
                }.toImmutableList()
                _uiState.update { currentState ->
                    currentState.copy(recommendedUserList = matchingCardList)
                }
            }
            .onFailure {
                _uiState.update { currentState ->
                    currentState.copy(recommendedUserList = persistentListOf())
                }
            }
    }

    fun fetchMatchedUser() = viewModelScope.launch {
        updateLoadState(HomeUiState.Loading)

        val dummyMatchedUser = createDummyMatchedUser()

        updateLoadState(HomeUiState.Success)

        _uiState.update { currentState ->
            currentState.copy(matchedUser = dummyMatchedUser)
        }
    }

    fun fetchRegionRankerList() = viewModelScope.launch {
        updateLoadState(HomeUiState.Loading)

        rankingRepository.getRankingList()
            .onSuccess { rankingData ->
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = HomeUiState.Success,
                        topRankerList = rankingData.topUsers.take(5).toImmutableList(),
                        regionRankerList = rankingData.topUsers.toImmutableList(),
                    )
                }
            }
            .onFailure { throwable ->
                updateLoadState(HomeUiState.Failure(throwable.message ?: "Unknown error"))
            }
    }

    private fun createDummyMatchedUser(): DummyMatchedUser? {
        return DummyMatchedUser(
            userId = "matchedUser1",
            nickname = "더미하는김에긴닉네임",
        )
    }

    private fun updateLoadState(state: HomeUiState) = _uiState.update { currentState ->
        currentState.copy(loadState = state)
    }
}
