package com.smashing.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.repository.api.EventRepository
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.RankingRepository
import com.smashing.app.data.repository.api.SearchRepository
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        observeSseEvents()
        fetchMatchedUser()
    }

    fun refreshHomeData() {
        fetchMyTierProfile()
        fetchRegionRankerList()
        fetchRecommendedUserList()
        fetchMatchedUser()
    }

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

    fun fetchRecommendedUserList() = viewModelScope.launch {
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

    fun fetchMatchedUser(snapshotAt: String? = null, cursor: String? = null) {
        viewModelScope.launch {
            fetchMatchedUserInternal(snapshotAt, cursor)
        }
    }

    private suspend fun fetchMatchedUserInternal(snapshotAt: String?, cursor: String?) {
        matchingRepository.getMeAcceptedMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = FETCH_SIZE,
            order = OrderType.OLDEST,
        )
            .onSuccess { cursorPage ->
                val activeMatching = cursorPage.items.firstOrNull { it.resultStatus != GameResultStatusType.CANCELED }
                
                if (activeMatching != null) {
                    _uiState.update { currentState ->
                        currentState.copy(matchedUser = activeMatching)
                    }
                } else if (cursorPage.cursor.hasNext) {
                    fetchMatchedUserInternal(cursorPage.cursor.snapshotAt, cursorPage.cursor.nextCursor)
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

    fun fetchSelectSportProfile(profileId: String) {
        val currentState = uiState.value
        val currentActiveProfile = currentState.activeUserProfile ?: return
        if (currentActiveProfile.profileId == profileId) return

        val selectedProfile =
            currentState.allUserProfiles.find { it.profileId == profileId } ?: return

        val optimisticList = currentState.allUserProfiles.map { profile ->
            profile.copy(isActive = profile.profileId == profileId)
        }.toImmutableList()

        val optimisticActiveProfile = currentActiveProfile.copy(
            profileId = selectedProfile.profileId,
            sportType = selectedProfile.sportCode,
        )

        _uiState.update {
            it.copy(
                allUserProfiles = optimisticList,
                activeUserProfile = optimisticActiveProfile,
            )
        }

        viewModelScope.launch {
            myRepository.switchActiveMyProfile(profileId)
                .onSuccess {
                    fetchMyTierProfile()
                    fetchRegionRankerList()
                    fetchRecommendedUserList()
                    fetchMatchedUser()
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
            GameResultStatusType.RESULT_CONFIRMED, GameResultStatusType.CANCELED -> {
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
                                latestAttemptNo = 1,
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
