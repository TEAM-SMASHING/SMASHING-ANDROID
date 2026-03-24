package com.smashing.app.presentation.notice

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.domain.model.Notification
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.notice.model.NoticeChangeSportUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface NoticeContract {

    @Immutable
    data class State(
        val noticeList: ImmutableList<Notification> = persistentListOf(),
        val loadState: NoticeUiState = NoticeUiState.Idle,
        val cursor: Cursor = Cursor(),
        val targetChangeSport: NoticeChangeSportUiModel = NoticeChangeSportUiModel(),
        val isChangeDialogVisible: Boolean = false,
    )

    sealed interface SideEffect {
        data class NavigateToMatching(val type: MatchingType) : SideEffect
        data class NavigateToConfirmReview(val reviewId: String) : SideEffect
    }
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
