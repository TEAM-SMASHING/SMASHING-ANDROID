package com.smashing.app.presentation.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.presentation.matching.type.MatchingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchingContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchReceivedMatchingList(isRefresh = true)
        fetchSentMatchingList(isRefresh = true)
        fetchAcceptedMatchingList(isRefresh = true)
    }

    // TODO SSE 연결 후 수정 예정
    fun selectMatchingTab(type: MatchingType) {
        updateMatchingType(type)
        when (type) {
            MatchingType.RECEIVE -> fetchReceivedMatchingList(true)
            MatchingType.SEND -> fetchSentMatchingList(true)
            MatchingType.ACCEPTED -> fetchAcceptedMatchingList(true)
        }
    }

    fun updateMatchingType(type: MatchingType) = _uiState.update {
        it.copy(selectedType = type)
    }

    fun showDialogVisible() = _uiState.update {
        it.copy(isDialogVisible = true)
    }

    fun hideDialogVisible() = _uiState.update {
        it.copy(isDialogVisible = false)
    }

    fun fetchMatchingList() {
        when (uiState.value.selectedType) {
            MatchingType.RECEIVE -> fetchReceivedMatchingList()
            MatchingType.SEND -> fetchSentMatchingList()
            MatchingType.ACCEPTED -> fetchAcceptedMatchingList()
        }
    }

    private fun fetchReceivedMatchingList(isRefresh: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.receivedUiState == MatchingUiState.Loading) return@launch
            if (!currentState.receivedCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(receivedUiState = MatchingUiState.Loading) }

        matchingRepository.getMeReceivedMatchingList(
            snapshotAt = if (isRefresh) null else currentState.receivedCursor.snapshotAt,
            cursor = if (isRefresh) null else currentState.receivedCursor.nextCursor,
            size = CURSOR_SIZE,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                state.copy(
                    receivedList = if (isRefresh) {
                        cursorPage.items.toImmutableList()
                    } else {
                        (state.receivedList + cursorPage.items).toImmutableList()
                    },
                    receivedCursor = cursorPage.cursor,
                    receivedUiState = if (cursorPage.items.isEmpty() && isRefresh) {
                        MatchingUiState.Empty
                    } else {
                        MatchingUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    receivedUiState = MatchingUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun fetchSentMatchingList(isRefresh: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.sentUiState == MatchingUiState.Loading) return@launch
            if (!currentState.sentCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(sentUiState = MatchingUiState.Loading) }

        matchingRepository.getMeSentMatchingList(
            snapshotAt = if (isRefresh) null else currentState.sentCursor.snapshotAt,
            cursor = if (isRefresh) null else currentState.sentCursor.nextCursor,
            size = CURSOR_SIZE,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                state.copy(
                    sentList = if (isRefresh) {
                        cursorPage.items.toImmutableList()
                    } else {
                        (state.sentList + cursorPage.items).toImmutableList()
                    },
                    sentCursor = cursorPage.cursor,
                    sentUiState = if (cursorPage.items.isEmpty() && isRefresh) {
                        MatchingUiState.Empty
                    } else {
                        MatchingUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    sentUiState = MatchingUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun fetchAcceptedMatchingList(isRefresh: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        if (!isRefresh) {
            if (currentState.acceptedUiState == MatchingUiState.Loading) return@launch
            if (!currentState.acceptedCursor.hasNext) return@launch
        }

        _uiState.update { it.copy(acceptedUiState = MatchingUiState.Loading) }

        matchingRepository.getMeAcceptedMatchingList(
            snapshotAt = if (isRefresh) null else currentState.acceptedCursor.snapshotAt,
            cursor = if (isRefresh) null else currentState.acceptedCursor.nextCursor,
            size = CURSOR_SIZE,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                state.copy(
                    acceptedList = if (isRefresh) {
                        cursorPage.items.toImmutableList()
                    } else {
                        (state.acceptedList + cursorPage.items).toImmutableList()
                    },
                    acceptedCursor = cursorPage.cursor,
                    acceptedUiState = if (cursorPage.items.isEmpty() && isRefresh) {
                        MatchingUiState.Empty
                    } else {
                        MatchingUiState.Success
                    },
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    acceptedUiState = MatchingUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    fun acceptReceivedMatching(
        matchingId: String,
    ) = viewModelScope.launch {
        matchingRepository.postAcceptedMatching(
            matchingId = matchingId,
        ).onSuccess {
            _uiState.update { currentState ->
                currentState.copy(
                    receivedList = _uiState.value.receivedList
                        .filter { it.matchingId != matchingId }
                        .toImmutableList()
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    acceptedUiState = MatchingUiState.Failure(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    companion object {
        private const val CURSOR_SIZE = 4L
        private const val TAG = "MatchingViewModel"
    }
}
