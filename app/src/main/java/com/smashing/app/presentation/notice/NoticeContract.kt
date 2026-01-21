package com.smashing.app.presentation.notice

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.domain.model.Notification
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface NoticeContract {

    @Immutable
    data class State(
        val noticeList: ImmutableList<Notification> = persistentListOf(),
        val loadState: NoticeUiState = NoticeUiState.Idle,
        val cursor: Cursor = Cursor(),
        val selectedNoticeItem: Notification = Notification(),
        val isChangeDialogVisible: Boolean = false,
    )
}

sealed interface NoticeUiState {
    data object Idle : NoticeUiState

    data object Loading : NoticeUiState

    data object Empty : NoticeUiState

    data object Success : NoticeUiState

    data class Failure(
        val msg: String,
    ) : NoticeUiState
}
