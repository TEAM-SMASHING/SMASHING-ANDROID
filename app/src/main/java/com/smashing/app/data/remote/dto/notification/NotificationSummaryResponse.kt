package com.smashing.app.data.remote.dto.notification

import com.smashing.app.data.type.NotificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationSummaryResponse(
    @SerialName("notificationId")
    val notificationId: String,
    @SerialName("type")
    val notificationType: NotificationType,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("linkUrl")
    val linkUrl: String?,
    @SerialName("isRead")
    val isRead: Boolean,
    @SerialName("createdAt")
    val createdAt: String,
)
