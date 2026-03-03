package com.smashing.app.presentation.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.repository.api.EventRepository
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.MatchingStatusType
import com.smashing.app.presentation.matching.MatchingContract.SideEffect
import com.smashing.app.presentation.matching.type.MatchingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchingViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchingContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchReceivedMatchingList(isRefresh = true)
        fetchSentMatchingList(isRefresh = true)
        fetchAcceptedMatchingList(isRefresh = true)
        observeSseEvents()
    }

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

    fun showDeleteSentMatchingDialog(matchingId: String) = _uiState.update {
        it.copy(
            isDialogVisible = true,
            selectedMatchingId = matchingId,
        )
    }

    fun showDeleteAcceptedMatchingDialog(gameId: String) = _uiState.update {
        it.copy(
            isDialogVisible = true,
            selectedGameId = gameId,
        )
    }

    fun hideDialogVisible() = _uiState.update {
        it.copy(
            isDialogVisible = false,
            selectedMatchingId = null,
            selectedGameId = null,
        )
    }

    fun fetchMatchingList() {
        when (uiState.value.selectedType) {
            MatchingType.RECEIVE -> fetchReceivedMatchingList()
            MatchingType.SEND -> fetchSentMatchingList()
            MatchingType.ACCEPTED -> fetchAcceptedMatchingList()
        }
    }

    fun refreshMatchingList() {
        when (uiState.value.selectedType) {
            MatchingType.RECEIVE -> fetchReceivedMatchingList(isRefresh = true)
            MatchingType.SEND -> fetchSentMatchingList(isRefresh = true)
            MatchingType.ACCEPTED -> fetchAcceptedMatchingList(isRefresh = true)
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
                val updatedList = if (isRefresh) cursorPage.items.toImmutableList()
                else (state.receivedList + cursorPage.items).toImmutableList()

                state.copy(
                    receivedList = updatedList,
                    receivedCursor = cursorPage.cursor,
                    receivedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                )
            }
        }.onFailure { throwable ->
            updateReceivedUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
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
                val updatedList = if (isRefresh) cursorPage.items.toImmutableList()
                else (state.sentList + cursorPage.items).toImmutableList()

                state.copy(
                    sentList = updatedList,
                    sentCursor = cursorPage.cursor,
                    sentUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                )
            }
        }.onFailure { throwable ->
            updateSentUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
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
                val updatedList = if (isRefresh) cursorPage.items.toImmutableList()
                else (state.acceptedList + cursorPage.items).toImmutableList()

                state.copy(
                    acceptedList = updatedList,
                    acceptedCursor = cursorPage.cursor,
                    acceptedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                )
            }
        }.onFailure { throwable ->
            updateAcceptedUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
        }
    }

    fun acceptReceivedMatching(
        matchingId: String,
    ) = viewModelScope.launch {
        matchingRepository.postAcceptedMatching(
            matchingId = matchingId,
        ).onSuccess {
            _uiState.update { currentState ->
                val updatedList = currentState.receivedList
                    .filter { it.matchingId != matchingId }
                    .toImmutableList()
                currentState.copy(
                    receivedList = updatedList,
                    receivedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                )
            }
            _sideEffect.emit(SideEffect.ShowToast("매칭을 수락했어요! 매칭 확정 탭에서 확인해주세요."))
        }.onFailure { throwable ->
            updateReceivedUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
        }
    }

    fun rejectReceivedMatching(
        matchingId: String,
    ) = viewModelScope.launch {
        matchingRepository.postRejectMatching(
            matchingId = matchingId,
        ).onSuccess {
            _uiState.update { currentState ->
                val updatedList = currentState.receivedList
                    .filter { it.matchingId != matchingId }
                    .toImmutableList()
                currentState.copy(
                    receivedList = updatedList,
                    receivedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                )
            }
        }.onFailure { throwable ->
            updateReceivedUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
        }
    }

    fun deleteSentMatching() = viewModelScope.launch {
        val matchingId = _uiState.value.selectedMatchingId ?: return@launch
        hideDialogVisible()

        matchingRepository.deleteSentMatching(
            matchingId = matchingId,
        ).onSuccess {
            _uiState.update { currentState ->
                val updatedList = currentState.sentList
                    .filter { it.matchingId != matchingId }
                    .toImmutableList()
                currentState.copy(
                    sentList = updatedList,
                    sentUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                )
            }
        }.onFailure { throwable ->
            updateSentUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
        }
    }

    fun confirmDeleteAcceptedMatching() = viewModelScope.launch {
        val gameId = _uiState.value.selectedGameId ?: return@launch
        hideDialogVisible()

        matchingRepository.putCancelGame(gameId).onSuccess {
            _uiState.update { state ->
                state.copy(
                    acceptedList = state.acceptedList.map { matching ->
                        if (matching.gameId == gameId) {
                            matching.copy(resultStatus = GameResultStatusType.CANCELED)
                        } else {
                            matching
                        }
                    }.toImmutableList()
                )
            }
        }.onFailure { throwable ->
            updateAcceptedUiState(
                MatchingUiState.Failure(throwable.message ?: "Unknown error")
            )
        }
    }

    fun handleAcceptedMatchingClick(matching: AcceptedMatching) = viewModelScope.launch {
        when (matching.resultStatus) {
            GameResultStatusType.PENDING_RESULT -> {
                _sideEffect.emit(
                    SideEffect.NavigateToSubmit(
                        gameId = matching.gameId,
                        opponentUserId = matching.userId,
                        opponentNickname = matching.nickname,
                        isFirstAttempt = true,
                    )
                )
            }

            GameResultStatusType.RESULT_REJECTED -> {
                val submissionId = matching.latestSubmissionId ?: return@launch
                _sideEffect.emit(
                    SideEffect.NavigateToSubmit(
                        gameId = matching.gameId,
                        opponentUserId = matching.userId,
                        opponentNickname = matching.nickname,
                        isFirstAttempt = false,
                        submissionId = submissionId,
                    )
                )
            }

            GameResultStatusType.WAITING_CONFIRMATION -> {
                val submissionId = matching.latestSubmissionId ?: return@launch
                val isFirstAttempt = matching.latestAttemptNo == 1
                _sideEffect.emit(
                    SideEffect.NavigateToConfirm(
                        submissionId = submissionId,
                        gameId = matching.gameId,
                        isFirstAttempt = isFirstAttempt,
                    )
                )
            }

            else -> Unit
        }
    }

    private fun updateReceivedUiState(uiState: MatchingUiState) = _uiState.update {
        it.copy(receivedUiState = uiState)
    }

    private fun updateSentUiState(uiState: MatchingUiState) = _uiState.update {
        it.copy(sentUiState = uiState)
    }

    private fun updateAcceptedUiState(uiState: MatchingUiState) = _uiState.update {
        it.copy(acceptedUiState = uiState)
    }

    private fun observeSseEvents() = viewModelScope.launch {
        eventRepository.events.collect { event ->
            when (event) {
                is SseEvent.MatchingUpdated -> handleMatchingUpdated(event)
                is SseEvent.MatchingReceived -> handleMatchingReceived(event)
                is SseEvent.GameUpdated -> handleGameUpdated(event)
                else -> Unit
            }
        }
    }

    private fun handleMatchingUpdated(event: SseEvent.MatchingUpdated) {
        when (event.status) {
            MatchingStatusType.CANCELLED -> {
                _uiState.update { currentState ->
                    val updatedList = currentState.receivedList
                        .filter { it.matchingId != event.matchingId }
                        .toImmutableList()
                    currentState.copy(
                        receivedList = updatedList,
                        receivedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                    )
                }
            }

            MatchingStatusType.ACCEPTED, MatchingStatusType.REJECTED -> {
                _uiState.update { currentState ->
                    val updatedList = currentState.sentList
                        .filter { it.matchingId != event.matchingId }
                        .toImmutableList()
                    currentState.copy(
                        sentList = updatedList,
                        sentUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                    )
                }
            }
        }
    }

    private fun handleMatchingReceived(event: SseEvent.MatchingReceived) {
        val newMatching = ReceivedMatching(
            matchingId = event.matchingId,
            userId = event.requester.userId,
            nickname = event.requester.nickname,
            genderType = event.requester.genderType,
            tierType = event.requester.tierType,
            reviewCount = event.requester.reviewCount,
            winCount = event.requester.winCount,
            loseCount = event.requester.loseCount,
            createdAt = "",
        )

        _uiState.update { currentState ->
            val updatedList = (listOf(newMatching) + currentState.receivedList).toImmutableList()
            currentState.copy(
                receivedList = updatedList,
                receivedUiState = MatchingUiState.Success,
            )
        }
    }

    private fun handleGameUpdated(event: SseEvent.GameUpdated) {
        when (event.resultStatus) {
            GameResultStatusType.RESULT_CONFIRMED -> {
                _uiState.update { currentState ->
                    val updatedList = currentState.acceptedList
                        .filter { it.gameId != event.gameId }
                        .toImmutableList()
                    currentState.copy(
                        acceptedList = updatedList,
                        acceptedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                    )
                }
            }

            GameResultStatusType.CANCELED -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        acceptedList = currentState.acceptedList.map { matching ->
                            if (matching.gameId == event.gameId) {
                                matching.copy(resultStatus = GameResultStatusType.CANCELED)
                            } else {
                                matching
                            }
                        }.toImmutableList()
                    )
                }
            }

            GameResultStatusType.WAITING_CONFIRMATION -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        acceptedList = currentState.acceptedList.map { matching ->
                            if (matching.gameId == event.gameId) {
                                matching.copy(
                                    resultStatus = GameResultStatusType.WAITING_CONFIRMATION,
                                    latestSubmissionId = event.submissionId,
                                    latestAttemptNo = event.attemptNo,
                                )
                            } else {
                                matching
                            }
                        }.toImmutableList()
                    )
                }
            }

            GameResultStatusType.RESULT_REJECTED -> {
                _uiState.update { currentState ->
                    val targetMatching =
                        currentState.acceptedList.find { it.gameId == event.gameId }
                    val shouldDelete = (targetMatching?.latestAttemptNo ?: 0) >= 2

                    if (shouldDelete) {
                        val updatedList = currentState.acceptedList
                            .filter { it.gameId != event.gameId }
                            .toImmutableList()
                        currentState.copy(
                            acceptedList = updatedList,
                            acceptedUiState = if (updatedList.isEmpty()) MatchingUiState.Empty else MatchingUiState.Success,
                        )
                    } else {
                        currentState.copy(
                            acceptedList = currentState.acceptedList.map { matching ->
                                if (matching.gameId == event.gameId) {
                                    matching.copy(
                                        resultStatus = GameResultStatusType.RESULT_REJECTED,
                                        latestSubmissionId = event.submissionId,
                                        latestAttemptNo = 1,
                                    )
                                } else {
                                    matching
                                }
                            }.toImmutableList()
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    companion object {
        private const val CURSOR_SIZE = 20L
    }
}
