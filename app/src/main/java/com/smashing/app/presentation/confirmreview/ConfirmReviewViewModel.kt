package com.smashing.app.presentation.confirmreview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.smashing.app.data.repository.api.ReviewRepository
import com.smashing.app.presentation.confirmreview.navigation.ConfirmReview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ConfirmReview>()
    private val reviewId = route.reviewId

    private val _uiState = MutableStateFlow(ConfirmReviewContract.State())
    val uiState = _uiState.asStateFlow()



    init {
        fetchReviewDetail()
    }

    private fun fetchReviewDetail() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        reviewRepository.getReview(reviewId = reviewId)
            .onSuccess { review ->
                _uiState.update { state ->
                    state.copy(
                        nickname = review.reviewerNickname,
                        reviewRatingType = review.rating,
                        reviewText = review.content,
                        tags = review.tags.map { it.tagLabel }.toImmutableList(),
                        isLoading = false,
                    )
                }
            }
            .onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
    }
}
