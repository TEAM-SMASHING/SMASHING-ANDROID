package com.smashing.app.presentation.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smashing.app.data.repository.api.MyRepository
import com.smashing.app.data.repository.api.NotificationRepository
import com.smashing.app.data.type.NotificationType
import com.smashing.app.domain.model.Notification
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.notice.model.NoticeChangeSportUiModel
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
class NoticeViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NoticeContract.State())
    val uiState = _uiState.asStateFlow()
    private val _sideEffect = MutableSharedFlow<NoticeContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchNotificationList(isRefresh = true)
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

    private suspend fun readNotification(notificationId: String) {
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

    fun updateTargetChangeSport(
        targetChangeSport: NoticeChangeSportUiModel,
    ) = _uiState.update {
        it.copy(
            targetChangeSport = targetChangeSport,
        )
    }

    fun onNoticeClick(notice: Notification) = viewModelScope.launch {
        notificationRepository.getNotificationSportMatch(
            notificationId = notice.notificationId,
        ).onSuccess { sportMatch ->
            if (sportMatch.isMatch) {
                handleNoticeNavigation(notice)
            } else {
                updateTargetChangeSport(
                    targetChangeSport = NoticeChangeSportUiModel(
                        sportType = sportMatch.receiverSportType,
                        profileId = sportMatch.receiverUserProfileId,
                        noticeItem = notice,
                    )
                )
                updateIsChangeDialogVisible(true)
            }
        }.onFailure {
            updateNoticeUiState(NoticeUiState.Failure("유효하지 않은 알림입니다."))
        }
    }

    fun changeMyProfile() = viewModelScope.launch {
        val targetProfileId = _uiState.value.targetChangeSport.profileId ?: return@launch
        myRepository.switchActiveMyProfile(targetProfileId)
            .onSuccess {
                updateIsChangeDialogVisible(false)
                val selectedNotice = _uiState.value.targetChangeSport.noticeItem
                if (selectedNotice.notificationId.isNotEmpty()) {
                    handleNoticeNavigation(selectedNotice)
                }
            }
            .onFailure {
                updateNoticeUiState(NoticeUiState.Failure(it.message ?: "프로필 전환 실패"))
                updateIsChangeDialogVisible(false)
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

    private suspend fun handleNoticeNavigation(notice: Notification) {
        if (!notice.isRead) {
            readNotification(notice.notificationId)
        }
        when (notice.notificationType) {
            NotificationType.MATCHING_REQUESTED -> {
                _sideEffect.emit(NoticeContract.SideEffect.NavigateToMatching(MatchingType.RECEIVE))
            }

            NotificationType.MATCHING_ACCEPTED,
            NotificationType.MATCHING_RESULT_SUBMITTED,
            NotificationType.MATCHING_RESULT_REJECTED,
                -> _sideEffect.emit(NoticeContract.SideEffect.NavigateToMatching(MatchingType.ACCEPTED))

            NotificationType.REVIEW_RECEIVED -> {
                notice.reviewId?.let { id ->
                    _sideEffect.emit(NoticeContract.SideEffect.NavigateToConfirmReview(id))
                }
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20L
    }
}
