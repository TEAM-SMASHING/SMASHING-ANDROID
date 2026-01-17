package com.smashing.app.data.model.notification

import com.smashing.app.data.type.NotificationType

data class NotificationSummary(
    val notificationId: String,
    val type: NotificationType,
    val title: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: String,
)
