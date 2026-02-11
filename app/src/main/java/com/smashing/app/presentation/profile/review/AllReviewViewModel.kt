package com.smashing.app.presentation.profile.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.presentation.profile.navigation.Review
import com.smashing.app.presentation.profile.review.ReviewContract.ReviewUiState
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.UserProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
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

    private var nextCursor: String? = null
    private var hasNextPage: Boolean = true
    private var isLoading: Boolean = false

    init {
        if (userId == null && isUser) {
            fetchMyProfileReview(true)
            fetchMyRecentReviewStats()
        } else {
            fetchUserRecentReviewStats()
            fetchUserProfileReview(true)
        }
    }

    fun fetchUserProfileReview(isRefresh: Boolean = false) = viewModelScope.launch {

        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.reviewUiState == UserProfileUiState.Loading) return@launch
            if (!currentState.reviewCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(reviewUiState = ReviewUiState.Loading) }

        if (userId != null) {
            reviewRepository.getUserRecentReviewList(
                userId = userId,
                sportCode = sportCode,
                cursor = if (isRefresh) null else currentState.reviewCursor.nextCursor,
                size = CURSOR_SIZE,
            ).onSuccess { cursorPage ->
                _uiState.update { state ->
                    state.copy(
                        gameReview = if (isRefresh) {
                            cursorPage.items.toImmutableList()
                        } else {
                            (state.gameReview + cursorPage.items).toImmutableList()
                        },
                        reviewCursor = cursorPage.cursor,
                        reviewUiState = if (cursorPage.items.isEmpty() && isRefresh) {
                            ReviewUiState.Idle
                        } else {
                            ReviewUiState.Success
                        },
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        reviewUiState = ReviewUiState.Failure(
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

    fun fetchMyProfileReview(isInit: Boolean = false) {
        if (isLoading || (!isInit && !hasNextPage)) return

        viewModelScope.launch {
            isLoading = true

            if (isInit) {
                _uiState.update {
                    it.copy(reviewUiState = ReviewUiState.Loading)
                }
                nextCursor = null
            }

            reviewRepository.getMyRecentReviewList(
                cursor = if (isInit) null else nextCursor,
                size = PAGE_SIZE
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
                            reviewUiState = ReviewUiState.Success,
                            gameReview = newReviews,
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            reviewUiState = ReviewUiState.Failure(
                                exception.message ?: "리뷰를 불러오는데 실패했습니다.",
                            )
                        )
                    }
                }
            isLoading = false
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
        private const val PAGE_SIZE = 50
    }
}
