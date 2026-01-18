package com.smashing.app.presentation.profile.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.UserRepository
import com.smashing.app.presentation.profile.userprofile.UserProfileContract
import com.smashing.app.presentation.profile.userprofile.UserProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllReviewViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfileReview(isRefresh = true)
    }

    fun fetchProfileReview(isRefresh: Boolean = false) = viewModelScope.launch {

        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.userProfileUiState == UserProfileUiState.Loading) return@launch
            if (!currentState.userProfileCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(userProfileUiState = UserProfileUiState.Loading) }

        userRepository.getUserRecentList(
            userId = "0P6W8QCQBR4FV",//currentState.userId,
            sportCode = "BM",//currentState.selectedSportProfileId,
            cursor = if (isRefresh) null else currentState.userProfileCursor.nextCursor,
            size = CURSOR_SIZE,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                state.copy(
                    gameReview = if (isRefresh) {
                        cursorPage.items.toImmutableList()
                    } else {
                        (state.gameReview + cursorPage.items).toImmutableList()
                    },
                    userProfileCursor = cursorPage.cursor,
                    userProfileUiState = if (cursorPage.items.isEmpty() && isRefresh) {
                        UserProfileUiState.Idle
                    } else {
                        UserProfileUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    userProfileUiState = UserProfileUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    companion object {
        private const val CURSOR_SIZE = 5
    }
}
