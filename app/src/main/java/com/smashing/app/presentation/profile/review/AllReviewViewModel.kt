package com.smashing.app.presentation.profile.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.presentation.profile.myprofile.MyProfileContract
import com.smashing.app.presentation.profile.myprofile.MyProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class AllReviewViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = MyProfileContract.State())
    val uiState: StateFlow<MyProfileContract.State> = _uiState.asStateFlow()

    private val pageSize = 50
    private var nextCursor: String? = null
    private var hasNextPage: Boolean = true
    private var isLoading: Boolean = false

    init {
        fetchReviews(isInit = true)
    }

    fun fetchReviews(isInit: Boolean = false) {
        if (isLoading || (!isInit && !hasNextPage)) return

        viewModelScope.launch {
            isLoading = true

            if (isInit) {
                _uiState.update {it.copy(reviewLoadState = MyProfileUiState.Loading)
                }
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
                            reviewLoadState = MyProfileUiState.Success,
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
            isLoading = false
        }
    }
}
