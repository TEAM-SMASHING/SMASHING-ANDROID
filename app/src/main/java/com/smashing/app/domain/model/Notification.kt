package com.smashing.app.domain.model

import com.smashing.app.data.type.NotificationType
import com.smashing.app.data.type.SportType

data class Notification(
    val notificationId: String = "",
    val userId: String = "",
    val nickname: String = "",
    val sportType: SportType = SportType.PING_PONG,
    val notificationType: NotificationType = NotificationType.REVIEW_RECEIVED,
    val title: String = "",
    val description: String = "",
    val isRead: Boolean = false,
    val timeAgo: String = "",
)
