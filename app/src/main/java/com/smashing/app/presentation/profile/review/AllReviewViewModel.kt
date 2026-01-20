package com.smashing.app.presentation.profile.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.presentation.profile.navigation.Review
import com.smashing.app.presentation.profile.review.ReviewContract.ReviewUiState
import com.smashing.app.presentation.profile.userprofile.UserProfileContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<Review>().userId
    private val isUser = savedStateHandle.toRoute<Review>().isUser

    private val _uiState = MutableStateFlow(ReviewContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        if (userId == null && !isUser) {
            //Todo: 나의 리뷰로 이동
        } else {
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
                sportCode = "BM", //Todo: 실제 값으로 수정
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
                            throwable.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val CURSOR_SIZE = 50
    }
}
