package com.smashing.app.presentation.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.NotificationRepository
import com.smashing.app.data.repository.api.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val myRepository: ReviewRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NoticeContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchNotificationList()
    }

    private fun fetchNotificationList(isRefresh: Boolean = false) = viewModelScope.launch {
        val currentState = _uiState.value

        val isFirstFetch = currentState.cursor.snapshotAt == null
        if (!isRefresh && !isFirstFetch && !currentState.cursor.hasNext) return@launch

        updateNoticeUiState(NoticeUiState.Loading)

        notificationRepository.getNotificationList(
            snapshotAt = if (isRefresh) null else currentState.cursor.snapshotAt,
            cursor = if (isRefresh) null else currentState.cursor.nextCursor,
            size = PAGE_SIZE,
        ).onSuccess { cursorPage ->
            _uiState.update { state ->
                val updatedList = if (isRefresh) cursorPage.items.toImmutableList()
                else (state.noticeList + cursorPage.items).toImmutableList()

                state.copy(
                    noticeList = updatedList,
                    cursor = cursorPage.cursor,
                    loadState = if (updatedList.isEmpty()) NoticeUiState.Empty else NoticeUiState.Success,
                )
            }
        }.onFailure { throwable ->
            updateNoticeUiState(
                uiState = NoticeUiState.Failure(throwable.message ?: "")
            )
        }
    }

    fun loadMore() {
        val state = _uiState.value
        if (state.cursor.hasNext && state.loadState !is NoticeUiState.Loading) {
            fetchNotificationList()
        }
    }

    fun readNotification(notificationId: String) = viewModelScope.launch {
        notificationRepository.putNotificationRead(
            notificationId = notificationId,
        ).onSuccess {
            _uiState.update { state ->
                val updatedList = state.noticeList.map { notice ->
                    if (notice.notificationId == notificationId && !notice.isRead)
                        notice.copy(isRead = true)
                    else notice
                }.toImmutableList()

                state.copy(noticeList = updatedList)
            }
        }.onFailure {
            updateNoticeUiState(NoticeUiState.Failure(it.message ?: "Read Notification Error"))
        }
    }

    private fun updateNoticeUiState(uiState: NoticeUiState) = _uiState.update {
        it.copy(
            loadState = uiState,
        )
    }

    fun updateIsChangeDialogVisible(isVisible: Boolean) = _uiState.update {
        it.copy(
            isChangeDialogVisible = isVisible,
        )
    }

    companion object {
        private const val PAGE_SIZE = 20L
    }
}
