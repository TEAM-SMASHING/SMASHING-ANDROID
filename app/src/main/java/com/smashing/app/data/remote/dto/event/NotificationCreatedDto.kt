package com.smashing.app.data.remote.dto.event

import com.smashing.app.data.type.NotificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationCreatedDto(
    @SerialName("type")
    val type: String,
    @SerialName("notificationId")
    val notificationId: String,
    @SerialName("notificationType")
    val notificationType: NotificationType,
    @SerialName("targetId")
    val targetId: String? = null,
)
