package com.smashing.app.presentation.notice.model

import com.smashing.app.data.type.SportType
import com.smashing.app.domain.model.Notification

data class NoticeChangeSportUiModel(
    val sportType: SportType = SportType.PING_PONG,
    val profileId: String? = null,
    val noticeItem: Notification = Notification(),
)
