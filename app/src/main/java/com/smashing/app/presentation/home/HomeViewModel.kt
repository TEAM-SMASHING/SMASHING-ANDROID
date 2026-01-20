package com.smashing.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.data.model.my.ActiveUserProfile
import com.smashing.app.data.model.my.UserProfileItem
import com.smashing.app.data.repository.api.RankingRepository
import com.smashing.app.data.repository.api.SearchRepository
import com.smashing.app.presentation.home.type.DummyMatchedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rankingRepository: RankingRepository,
    private val searchRepository: SearchRepository,
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchActiveProfile()
        fetchAllUserProfiles()
        fetchRegionRankerList()
        fetchRecommendedUserList()
        fetchMatchedUser()
    }

    fun fetchActiveProfile() = viewModelScope.launch {
        updateLoadState(HomeUiState.Loading)

        val dummyActiveProfile = createDummyActiveProfile()

        updateLoadState(HomeUiState.Success)

        _uiState.update { currentState ->
            currentState.copy(activeUserProfile = dummyActiveProfile)
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

    fun fetchAllUserProfiles() = viewModelScope.launch {
        updateLoadState(HomeUiState.Loading)

        val dummyAllUserProfiles = createDummyAllUserProfiles()

        updateLoadState(HomeUiState.Success)

        _uiState.update { currentState ->
            currentState.copy(allUserProfiles = dummyAllUserProfiles)
        }
    }

    private fun createDummyActiveProfile(): ActiveUserProfile {
        return ActiveUserProfile(
            nickname = "Test",
            region = "서울",
            profileId = "0USP111222333",
            sportType = SportType.TENNIS,
            tierType = TierType.GOLD_1,
            lp = 123,
            minLp = 100,
            maxLp = 500,
            wins = 10,
            losses = 7,
        )
    }

    private fun createDummyMatchedUser(): DummyMatchedUser? {
        return DummyMatchedUser(
            userId = "matchedUser1",
            nickname = "더미하는김에긴닉네임",
        )
    }

    private fun createDummyAllUserProfiles(): ImmutableList<UserProfileItem> {
        return listOf(
            UserProfileItem(
                profileId = "0USP111222333",
                sportCode = SportType.TENNIS,
                isActive = true,
            ),
            UserProfileItem(
                profileId = "0USP111222333",
                sportCode = SportType.PING_PONG,
                isActive = false,
            ),
            UserProfileItem(
                profileId = "0USP111222333",
                sportCode = SportType.BADMINTON,
                isActive = false,
            ),
        ).toImmutableList()
    }


    private fun updateLoadState(state: HomeUiState) = _uiState.update { currentState ->
        currentState.copy(loadState = state)
    }
}
