package com.smashing.app.presentation.notice

import androidx.compose.runtime.Immutable
import com.smashing.app.domain.model.Notification
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface NoticeContract {

    @Immutable
    data class State(
        val noticeList: ImmutableList<Notification> = persistentListOf(),
    )
}
