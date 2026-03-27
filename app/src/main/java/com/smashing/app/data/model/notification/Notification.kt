package com.smashing.app.data.model.notification

import com.smashing.app.data.type.NotificationType

data class Notification(
    val notificationId: String = "",
    val notificationType: NotificationType = NotificationType.REVIEW_RECEIVED,
    val title: String = "",
    val description: String = "",
    val isRead: Boolean = false,
    val timeAgo: String = "",
    val reviewId: String? = null,
)
