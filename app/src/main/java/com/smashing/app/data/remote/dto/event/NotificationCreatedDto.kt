package com.smashing.app.data.remote.dto.event

import kotlinx.serialization.Serializable

@Serializable
data class NotificationCreatedDto(
    val type: String,
    val notificationId: String,
    val notificationType: String,
    val targetId: String? = null,
)
