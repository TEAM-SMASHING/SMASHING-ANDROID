package com.smashing.app.presentation.profile.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.presentation.profile.navigation.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class AllReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val myRepository: MyRepository,
) : ViewModel() {

    private val userData = savedStateHandle.toRoute<Review>()

    private val userId = userData.userId
    private val sportCode = userData.sportCode
    private val isUser = userData.isUser

    private val _uiState = MutableStateFlow(ReviewContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        if (userId == null && isUser) {
            fetchMyProfileReviewList(true)
            fetchMyRecentReviewStats()
        } else {
            fetchUserRecentReviewStats()
            fetchUserProfileReviewList(true)
        }
    }

    fun loadMoreReviewList() {
        if (userId == null && isUser) {
            fetchMyProfileReviewList()
        } else {
            fetchUserProfileReviewList()
        }
    }

    fun fetchUserProfileReviewList(isRefresh: Boolean = false) = viewModelScope.launch {

        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.loadState == ReviewUiState.Loading) return@launch
            if (!currentState.reviewCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(loadState = ReviewUiState.Loading) }

        if (userId != null) {
            reviewRepository.getUserRecentReviewList(
                userId = userId,
                sportCode = sportCode,
                cursor = if (isRefresh) null else currentState.reviewCursor.nextCursor,
                size = CURSOR_SIZE,
                snapshotAt = if (isRefresh) null else currentState.reviewCursor.snapshotAt,
            ).onSuccess { cursorPage ->
                _uiState.update { state ->
                    state.copy(
                        gameReview = if (isRefresh) {
                            cursorPage.items.toImmutableList()
                        } else {
                            (state.gameReview + cursorPage.items).toImmutableList()
                        },
                        reviewCursor = cursorPage.cursor,
                        loadState = if (cursorPage.items.isEmpty() && isRefresh) {
                            ReviewUiState.Idle
                        } else {
                            ReviewUiState.Success
                        },
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        loadState = ReviewUiState.Failure(
                            throwable.message ?: "Unknown error",
                        )
                    )
                }
            }
        }
    }

    fun fetchUserRecentReviewStats() = viewModelScope.launch {
        if (userId != null) {
            userRepository.getUserRecentReviewStats(
                userId = userId,
                sportCode = sportCode,
            ).onSuccess { data ->
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = ReviewUiState.Success,
                        gameReviewResult = data,
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        loadState = ReviewUiState.Failure(
                            exception.message ?: "오류 발생",
                        )
                    )
                }
            }
        }
    }

    fun fetchMyProfileReviewList(isInit: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        if (!isInit) {
            if (currentState.loadState == ReviewUiState.Loading) return@launch
            if (!currentState.reviewCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(loadState = ReviewUiState.Loading) }

        reviewRepository.getMyRecentReviewList(
            cursor = if (isInit) null else currentState.reviewCursor.nextCursor,
            size = CURSOR_SIZE,
            snapshotAt = if (isInit) null else currentState.reviewCursor.snapshotAt,
        )
            .onSuccess { cursorPage ->
                _uiState.update { state ->
                    state.copy(
                        gameReview = if (isInit) {
                            cursorPage.items.toImmutableList()
                        } else {
                            (state.gameReview + cursorPage.items).toImmutableList()
                        },
                        reviewCursor = cursorPage.cursor,
                        loadState = if (cursorPage.items.isEmpty() && isInit) {
                            ReviewUiState.Idle
                        } else {
                            ReviewUiState.Success
                        }
                    )
                }
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        loadState = ReviewUiState.Failure(
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
                    loadState = ReviewUiState.Success,
                    gameReviewResult = data,
                )
            }
        }.onFailure { exception ->
            _uiState.update {
                it.copy(
                    loadState = ReviewUiState.Failure(
                        exception.message ?: "오류 발생"
                    )
                )
            }
        }
    }

    companion object {
        private const val CURSOR_SIZE = 50
    }
}
