package com.smashing.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.repository.api.EventRepository
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.RankingRepository
import com.smashing.app.data.repository.api.SearchRepository
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.OrderType
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
    private val matchingRepository: MatchingRepository,
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        observeSseEvents()
    }

    fun fetchHome() {
        fetchMyTierProfile()
        fetchRegionRankerList()
        fetchRecommendedUserList()
        fetchMatchedUser()
    }

    private fun fetchMyTierProfile() = viewModelScope.launch {
        myRepository.getMyTierProfile()
            .onSuccess { myTierProfile ->
                userRepository.setUserInfo(
                    userProfileId = myTierProfile.myProfileInfo.profileId,
                    userNickname = myTierProfile.nickname,
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        activeMyProfile = myTierProfile
                    )
                }
            }
            .onFailure { throwable ->
                Timber.tag("HomeViewModel").e(throwable, "Failed to fetch my tier profile")
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = HomeUiState.Failure(
                            throwable.message ?: "프로필을 불러오는데 실패했습니다."
                        )
                    )
                }
            }
    }

    private fun fetchRecommendedUserList() = viewModelScope.launch {
        searchRepository.getRecommendedUsers()
            .onSuccess { recommendedUsers ->
                _uiState.update { currentState ->
                    currentState.copy(recommendedUserList = recommendedUsers.toImmutableList())
                }
            }
            .onFailure {
                _uiState.update { currentState ->
                    currentState.copy(recommendedUserList = persistentListOf())
                }
            }
    }

    private fun fetchMatchedUser(snapshotAt: String? = null, cursor: String? = null) {
        viewModelScope.launch {
            fetchMatchedUserInternal(snapshotAt, cursor)
        }
    }

    // TODO 서버와 이야기 후 해당 로직 수정 필요
    private suspend fun fetchMatchedUserInternal(snapshotAt: String?, cursor: String?) {
        matchingRepository.getMeAcceptedMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = FETCH_SIZE,
            order = OrderType.OLDEST,
        )
            .onSuccess { cursorPage ->
                val activeMatching = cursorPage.items.firstOrNull()

                if (activeMatching != null) {
                    _uiState.update { currentState ->
                        currentState.copy(matchedUser = activeMatching)
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(matchedUser = null)
                    }
                }
            }
            .onFailure { throwable ->
                Timber.tag("HomeViewModel").e(throwable, "Failed to fetch matched user")
                _uiState.update { currentState ->
                    currentState.copy(matchedUser = null)
                }
            }
    }


    private fun fetchRegionRankerList() = viewModelScope.launch {
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

    fun fetchSelectSportProfile(profileId: String) {
        val currentState = uiState.value
        val currentActiveProfile = currentState.activeMyProfile ?: return
        if (currentActiveProfile.myProfileInfo.profileId == profileId) return

        val selectedProfile =
            currentActiveProfile.myProfileItem.find { it.profileId == profileId } ?: return

        val optimisticList = currentState.activeMyProfile.myProfileItem.map { profile ->
            profile.copy(isActive = profile.profileId == profileId)
        }.toImmutableList()

        val optimisticActiveProfile = currentActiveProfile.copy(
            myProfileInfo = currentActiveProfile.myProfileInfo.copy(
                profileId = selectedProfile.profileId,
                sportType = selectedProfile.sportType,
            ),
            myProfileItem = optimisticList,
        )

        _uiState.update {
            it.copy(
                activeMyProfile = optimisticActiveProfile,
                matchedUser = null,
            )
        }

        viewModelScope.launch {
            myRepository.switchActiveMyProfile(profileId)
                .onSuccess {
                    fetchHome()
                }
                .onFailure { throwable ->
                    Timber.tag("HomeViewModel").e(throwable, "Failed to switch sport profile")
                    fetchMyTierProfile()
                }
        }
    }

    private fun updateLoadState(state: HomeUiState) = _uiState.update { currentState ->
        currentState.copy(loadState = state)
    }

    private fun observeSseEvents() = viewModelScope.launch {
        eventRepository.events.collect { event ->
            when (event) {
                is SseEvent.GameUpdated -> handleGameUpdated(event)
                else -> Unit
            }
        }
    }

    private fun handleGameUpdated(event: SseEvent.GameUpdated) {
        val currentMatchedUser = _uiState.value.matchedUser ?: return
        if (currentMatchedUser.gameId != event.gameId) return

        when (event.resultStatus) {
            GameResultStatusType.RESULT_CONFIRMED -> {
                _uiState.update { it.copy(matchedUser = null) }
                fetchMatchedUser()
            }

            GameResultStatusType.WAITING_CONFIRMATION -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        matchedUser = currentMatchedUser.copy(
                            resultStatus = GameResultStatusType.WAITING_CONFIRMATION,
                            latestSubmissionId = event.submissionId,
                            latestAttemptNo = event.attemptNo,
                        )
                    )
                }
            }

            GameResultStatusType.RESULT_REJECTED -> {
                val shouldDelete = (currentMatchedUser.latestAttemptNo ?: 0) >= 1

                if (shouldDelete) {
                    _uiState.update { it.copy(matchedUser = null) }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            matchedUser = currentMatchedUser.copy(
                                resultStatus = GameResultStatusType.RESULT_REJECTED,
                                latestSubmissionId = event.submissionId,
                                latestAttemptNo = event.attemptNo,
                            )
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    companion object {
        private const val FETCH_SIZE = 10L
    }
}
