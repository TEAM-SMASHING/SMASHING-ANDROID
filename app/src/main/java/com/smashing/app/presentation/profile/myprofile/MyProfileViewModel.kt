package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val pageSize = 50
    private var nextCursor: String? = null
    private var hasNextPage: Boolean = true
    private var isLoading: Boolean = false

    private val _uiState = MutableStateFlow(
        MyProfileContract.State(
            loadState = MyProfileUiState.Loading,
            profileInfo = ProfileInfo(
                profileId = "",
                sportType = SportType.PING_PONG,
                nickname = "",
                genderType = GenderType.MALE,
                tierType = TierType.GOLD_1,
                lp = 0,
                minLp = 0,
                maxLp = 1, winCount = 0,
                loseCount = 0,
                reviewCount = 0,
            ),
            sportProfileList = persistentListOf(),
            gameReview = persistentListOf()
        )
    )
    val uiState: StateFlow<MyProfileContract.State> = _uiState.asStateFlow()

    init {
        fetchReviews(isInit = true)
        fetchProfileInfo(isShowLoading = true)
    }

    private fun fetchProfileInfo(isShowLoading: Boolean = false) {
        viewModelScope.launch {
            if (isShowLoading) {
                _uiState.update { it.copy(loadState = MyProfileUiState.Loading) }
            }

            myRepository.getMyPageInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            loadState = MyProfileUiState.Success,
                            profileInfo = data.profileInfo,
                            sportProfileList = data.sportProfiles.toPersistentList(),
                            selectedSportProfileId = data.sportProfiles.find { it.isActive }?.profileId
                                ?: data.profileInfo.profileId
                        )
                    }
                }
                .onFailure { exception ->
                    if (isShowLoading) {
                        _uiState.update {
                            it.copy(
                                loadState = MyProfileUiState.Failure(
                                    exception.message ?: "오류 발생"
                                )
                            )
                        }
                    }
                }
        }
    }

    fun selectProfileId(profileId: String) {
        val currentState = uiState.value
        if (currentState.selectedSportProfileId == profileId) return

        val optimisticList = currentState.sportProfileList.map { profile ->
            if (profile.profileId == profileId) {
                profile.copy(isActive = true)
            } else {
                profile.copy(isActive = false)
            }
        }.toPersistentList()

        _uiState.update {
            it.copy(
                selectedSportProfileId = profileId,
                sportProfileList = optimisticList
            )
        }
        viewModelScope.launch {
            myRepository.switchActiveMyProfile(profileId)
                .onSuccess {
                    fetchProfileInfo(isShowLoading = false)
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            loadState = MyProfileUiState.Failure(exception.message ?: "프로필 변경 실패")
                        )
                    }
                    fetchProfileInfo(isShowLoading = false)
                }
        }
    }

    fun fetchReviews(isInit: Boolean = false) {
        if (isLoading || (!isInit && !hasNextPage)) return

        viewModelScope.launch {
            isLoading = true

            if (isInit) {
                _uiState.update { it.copy(loadState = MyProfileUiState.Loading) }
                nextCursor = null
            }

            myRepository.getMyGameReviews(
                cursor = if (isInit) null else nextCursor,
                size = pageSize
            )
                .onSuccess { page ->
                    nextCursor = page.cursor.nextCursor
                    hasNextPage = page.cursor.hasNext

                    _uiState.update { currentState ->
                        val newReviews = if (isInit) {
                            page.items.toPersistentList()
                        } else {
                            (currentState.gameReview + page.items).toPersistentList()
                        }

                        currentState.copy(
                            loadState = MyProfileUiState.Success,
                            gameReview = newReviews
                        )
                    }
                }
                .onFailure { exception ->
                    exception.printStackTrace()
                    _uiState.update {
                        it.copy(
                            loadState = MyProfileUiState.Failure(
                                exception.message ?: "리뷰를 불러오는데 실패했습니다."
                            )
                        )
                    }
                }
            isLoading = false
        }
    }
}
