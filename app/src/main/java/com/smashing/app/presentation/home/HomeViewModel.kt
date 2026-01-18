package com.smashing.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.core.designsystem.state.MatchingCardState
import com.smashing.app.data.model.profile.ActiveUserProfile
import com.smashing.app.data.model.profile.UserProfileItem
import com.smashing.app.data.model.rank.UserRank
import com.smashing.app.data.repository.api.RankingRepository
import com.smashing.app.presentation.home.type.DummyMatchedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
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

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchActiveProfile()
        fetchAllUserProfiles()
        fetchRegionRankerList()
        fetchMatchingCardList()
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

    fun fetchMatchingCardList() = viewModelScope.launch {
        updateLoadState(HomeUiState.Loading)

        val dummyMatchingCardList = createDummyMatchingCardList()

        updateLoadState(HomeUiState.Success)

        _uiState.update { currentState ->
            currentState.copy(matchingCardList = dummyMatchingCardList)
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
                Timber.d("Yesssss")
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

    private fun createDummyMatchingCardList(): ImmutableList<MatchingCardState.Search> {
        return listOf(
            MatchingCardState.Search(
                userId = "match1",
                nickname = "탁구의신",
                genderType = GenderType.MALE,
                tierType = TierType.DIAMOND_1,
                onProfileClick = {},
                winCount = 254,
                loseCount = 38,
                reviewCount = 32,
            ),
            MatchingCardState.Search(
                userId = "match2",
                nickname = "테니스마스터",
                genderType = GenderType.FEMALE,
                tierType = TierType.PLATINUM_2,
                onProfileClick = {},
                winCount = 180,
                loseCount = 45,
                reviewCount = 28,
            ),
            MatchingCardState.Search(
                userId = "match3",
                nickname = "배드민턴킹",
                genderType = GenderType.MALE,
                tierType = TierType.GOLD_1,
                onProfileClick = {},
                winCount = 150,
                loseCount = 60,
                reviewCount = 25,
            ),
        ).toImmutableList()
    }


    private fun createDummyTopRankerList(): ImmutableList<UserRank> {
        return listOf(
            UserRank(
                userId = "user1",
                nickname = "1위 유저",
                rank = 1,
                tier = TierType.CHALLENGER,
                lp = 2500,
            ),
            UserRank(
                userId = "user2",
                nickname = "열글자테스트중입니다",
                rank = 2,
                tier = TierType.CHALLENGER,
                lp = 2450,
            ),
            UserRank(
                userId = "user3",
                nickname = "1위 유저",
                rank = 3,
                tier = TierType.CHALLENGER,
                lp = 2400,
            ),
            UserRank(
                userId = "user4",
                nickname = "프로게이머",
                rank = 4,
                tier = TierType.DIAMOND_1,
                lp = 2350,
            ),
            UserRank(
                userId = "user5",
                nickname = "랭커킹커",
                rank = 5,
                tier = TierType.DIAMOND_1,
                lp = 2300,
            ),
            UserRank(
                userId = "user6",
                nickname = "승리만추구",
                rank = 6,
                tier = TierType.DIAMOND_2,
                lp = 2250,
            ),
            UserRank(
                userId = "user7",
                nickname = "플래티넘마스터",
                rank = 7,
                tier = TierType.DIAMOND_2,
                lp = 2200,
            ),
            UserRank(
                userId = "user8",
                nickname = "골드라이더",
                rank = 8,
                tier = TierType.DIAMOND_3,
                lp = 2150,
            ),
            UserRank(
                userId = "user9",
                nickname = "실버도전자",
                rank = 9,
                tier = TierType.PLATINUM_1,
                lp = 2100,
            ),
            UserRank(
                userId = "user10",
                nickname = "브론즈탈출",
                rank = 10,
                tier = TierType.PLATINUM_2,
                lp = 2050,
            ),
        ).toImmutableList()
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
