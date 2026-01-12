package com.smashing.app.domain.model

import com.smashing.app.core.common.type.NotificationType
import com.smashing.app.core.common.type.SportType

data class Notification(
    val notificationId: String,
    val userId: String,
    val sportType: SportType,
    val notificationType: NotificationType,
    val title: String,
    val description: String,
    val isRead: Boolean,
    val timeAgo: String,
)
