package com.smashing.app.presentation.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType
import com.smashing.app.presentation.matching.type.MatchingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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

    private val _uiState = MutableStateFlow(getDummyState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchReceivedMatchingList(isRefresh = true)
        fetchSentMatchingList(isRefresh = true)
        fetchAcceptedMatchingList(isRefresh = true)
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

    // TODO 더미 데이터 삭제 예정
    private fun getDummyState(): MatchingContract.State {
        val dummyAcceptedList = persistentListOf(
            AcceptedMatching(
                gameId = "game_1",
                resultStatus = GameResultStatusType.PENDING_RESULT,
                createdAt = "2025-01-15T10:30:00Z",
                userId = "user_101",
                nickname = "스매셔김",
                openChatUrl = "https://open.kakao.com/o/example1",
                genderType = GenderType.MALE,
                tierType = TierType.BRONZE_2,
                submitAvailableAt = "2025-01-15T11:30:00Z",
                remainingSeconds = 3600,
                isLocked = true,
            ),
            AcceptedMatching(
                gameId = "game_2",
                resultStatus = GameResultStatusType.WAITING_CONFIRMATION,
                createdAt = "2025-01-14T15:20:00Z",
                userId = "user_102",
                nickname = "배드민턴왕",
                openChatUrl = null,
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                submitAvailableAt = "2025-01-14T16:20:00Z",
                remainingSeconds = 0,
                isLocked = false,
            ),
            AcceptedMatching(
                gameId = "game_3",
                resultStatus = GameResultStatusType.RESULT_CONFIRMED,
                createdAt = "2025-01-13T09:00:00Z",
                userId = "user_103",
                nickname = "셔틀마스터",
                openChatUrl = "https://open.kakao.com/o/example3",
                genderType = GenderType.MALE,
                tierType = TierType.GOLD_3,
                submitAvailableAt = "2025-01-13T10:00:00Z",
                remainingSeconds = 0,
                isLocked = false,
            ),
            AcceptedMatching(
                gameId = "game_4",
                resultStatus = GameResultStatusType.PENDING_RESULT,
                createdAt = "2025-01-12T14:45:00Z",
                userId = "user_104",
                nickname = "코트킹",
                openChatUrl = null,
                genderType = GenderType.MALE,
                tierType = TierType.SILVER_2,
                submitAvailableAt = "2025-01-12T15:45:00Z",
                remainingSeconds = 2700,
                isLocked = true,
            ),
            AcceptedMatching(
                gameId = "game_5",
                resultStatus = GameResultStatusType.RESULT_REJECTED,
                createdAt = "2025-01-11T11:10:00Z",
                userId = "user_105",
                nickname = "스매시퀸",
                openChatUrl = "https://open.kakao.com/o/example5",
                genderType = GenderType.FEMALE,
                tierType = TierType.BRONZE_1,
                submitAvailableAt = "2025-01-11T12:10:00Z",
                remainingSeconds = 0,
                isLocked = false,
            ),
        )

        val dummyReceivedList = persistentListOf(
            ReceivedMatching(
                matchingId = "matching_received_1",
                userId = "user_201",
                nickname = "셔틀콕러버",
                genderType = GenderType.MALE,
                tierType = TierType.BRONZE_1,
                reviewCount = 12,
                winCount = 8,
                loseCount = 3,
                createdAt = "",
            ),
            ReceivedMatching(
                matchingId = "matching_received_2",
                userId = "user_202",
                nickname = "코트지배자",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_2,
                reviewCount = 27,
                winCount = 21,
                loseCount = 10,
                createdAt = "",
            ),
        )

        val dummySentList = persistentListOf(
            SentMatching(
                matchingId = "matching_sent_1",
                userId = "user_301",
                nickname = "드롭샷마스터",
                genderType = GenderType.MALE,
                tierType = TierType.BRONZE_3,
                reviewCount = 5,
                winCount = 3,
                loseCount = 1,
                createdAt = "",
            ),
            SentMatching(
                matchingId = "matching_sent_2",
                userId = "user_302",
                nickname = "백핸드요정",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                reviewCount = 18,
                winCount = 14,
                loseCount = 6,
                createdAt = "",
            ),
            SentMatching(
                matchingId = "matching_sent_2",
                userId = "user_302",
                nickname = "백핸드요정",
                genderType = GenderType.FEMALE,
                tierType = TierType.SILVER_1,
                reviewCount = 18,
                winCount = 14,
                loseCount = 6,
                createdAt = ""
            ),
        )

        return MatchingContract.State(
            selectedType = MatchingType.ACCEPTED,
            receivedList = dummyReceivedList,
            receivedCursor = Cursor(),
            receivedUiState = MatchingUiState.Success,
            sentList = dummySentList,
            sentCursor = Cursor(),
            sentUiState = MatchingUiState.Success,
            acceptedList = dummyAcceptedList,
            acceptedCursor = Cursor(),
            acceptedUiState = MatchingUiState.Success,
        )
    }

    companion object {
        private const val CURSOR_SIZE = 4L
        private const val TAG = "MatchingViewModel"
    }
}
