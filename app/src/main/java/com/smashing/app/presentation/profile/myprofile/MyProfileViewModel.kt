package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.my.MyProfileInfo
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.profile.myprofile.MyProfileContract.State
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
    private val myRepository: MyRepository,
    private val reviewRepository: ReviewRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        State(
            myProfileInfo = MyProfileInfo(
                nickname = "",
                genderType = GenderType.MALE,
                reviewCount = 0L,
                myProfileInfo = ProfileInfo(
                    profileId = "",
                    sportType = SportType.PING_PONG,
                    tierType = TierType.IRON,
                    lp = 0,
                    minLp = 0,
                    maxLp = 1,
                    winCount = 0,
                    loseCount = 0,
                ),
                myProfileItem = persistentListOf()
            ),
        )
    )
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun fetchProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(profileLoadState = MyProfileUiState.Loading) }
            myRepository.getMyProfileInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            profileLoadState = MyProfileUiState.Success,
                            myProfileInfo = data,
                            selectedSportProfileId = data.myProfileInfo.profileId,
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            profileLoadState = MyProfileUiState.Failure(
                                exception.message ?: "오류 발생",
                            )
                        )
                    }
                }
        }
    }

    fun selectProfileId(profileId: String) {
        val currentState = uiState.value
        if (currentState.selectedSportProfileId == profileId) return

        val currentInfo = currentState.myProfileInfo
        val optimisticList = currentState.sportProfileList.map { item ->
            item.copy(isActive = item.profileId == profileId)
        }
        val updatedMyProfileInfo = currentInfo.copy(
            myProfileItem = optimisticList,
        )

        _uiState.update {
            it.copy(
                selectedSportProfileId = profileId,
                myProfileInfo = updatedMyProfileInfo,
            )
        }
        viewModelScope.launch {
            myRepository.switchActiveMyProfile(profileId)
                .onSuccess {
                    fetchProfileInfo()
                    fetchMyProfileReviewList()
                    fetchMyRecentReviewStats()
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            profileLoadState = MyProfileUiState.Failure(
                                exception.message ?: "프로필 변경 실패",
                            )
                        )
                    }
                }
        }
    }

    fun fetchMyProfileReviewList() = viewModelScope.launch {
        _uiState.update {
            it.copy(reviewLoadState = MyProfileUiState.Loading)
        }

        reviewRepository.getMyRecentReviewList(
            cursor = null,
            size = PAGE_SIZE,
        )
            .onSuccess { page ->
                _uiState.update { currentState ->
                    currentState.copy(
                        reviewLoadState = MyProfileUiState.Success,
                        gameReview = page.items.toPersistentList(),
                    )
                }
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        reviewLoadState = MyProfileUiState.Failure(
                            exception.message ?: "리뷰를 불러오는데 실패했습니다.",
                        )
                    )
                }
            }
    }

    fun fetchMyRecentReviewStats() = viewModelScope.launch {
        myRepository.getMyRecentReviewStats(
        ).onSuccess { data ->
            _uiState.update { currentState ->
                currentState.copy(
                    reviewLoadState = MyProfileUiState.Success,
                    gameReviewResult = data,
                )
            }
        }.onFailure { exception ->
            _uiState.update {
                it.copy(
                    reviewLoadState = MyProfileUiState.Failure(
                        exception.message ?: "오류 발생",
                    )
                )
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 3
    }
}
