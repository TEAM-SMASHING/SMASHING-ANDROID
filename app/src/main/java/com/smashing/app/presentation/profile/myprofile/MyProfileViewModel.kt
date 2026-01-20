package com.smashing.app.presentation.profile.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var isReviewPaging: Boolean = false
    private val _uiState = MutableStateFlow(MyProfileContract.State())

    val uiState: StateFlow<MyProfileContract.State> = _uiState.asStateFlow()

    fun fetchProfileInfo(isShowLoading: Boolean = false) {
        viewModelScope.launch {
            if (isShowLoading) {
                _uiState.update { it.copy(profileLoadState = MyProfileUiState.Loading) }
            }
            myRepository.getMyPageInfo()
                .onSuccess { data ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            profileLoadState = MyProfileUiState.Success,
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
                                profileLoadState = MyProfileUiState.Failure(
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
                            profileLoadState = MyProfileUiState.Failure(
                                exception.message ?: "프로필 변경 실패"
                            )
                        )
                    }
                    fetchProfileInfo(isShowLoading = false)
                }
        }
    }

    fun fetchReviews(isInit: Boolean = false) {
        if (isReviewPaging) return

        viewModelScope.launch {
            isReviewPaging = true
            if (isInit) {
                _uiState.update {
                    it.copy(reviewLoadState = MyProfileUiState.Loading)
                }
            }

            myRepository.getMyGameReviews(
                cursor = null,
                size = PAGE_SIZE
            )
                .onSuccess { page ->
                    _uiState.update { currentState ->
                        val newReviews = if (isInit) {
                            page.items.toPersistentList()
                        } else {
                            (currentState.gameReview + page.items).toPersistentList()
                        }

                        currentState.copy(
                            reviewLoadState = MyProfileUiState.Success, // 리뷰 성공
                            gameReview = newReviews
                        )
                    }
                }
                .onFailure { exception ->
                    exception.printStackTrace()
                    _uiState.update {
                        it.copy(
                            reviewLoadState = MyProfileUiState.Failure(
                                exception.message ?: "리뷰를 불러오는데 실패했습니다."
                            )
                        )
                    }
                }
            isReviewPaging = false
        }
    }

    companion object {
        private const val PAGE_SIZE = 3
    }
}
