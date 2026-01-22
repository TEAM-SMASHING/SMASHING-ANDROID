package com.smashing.app.data.remote.dto.event

import com.smashing.app.data.remote.dto.event.common.UserSummaryDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewReceivedNotificationDto(
    @SerialName("type")
    val type: String,
    @SerialName("notificationId")
    val notificationId: String,
    @SerialName("notificationType")
    val notificationType: String,
    @SerialName("notificationCreatedAt")
    val notificationCreatedAt: String,
    @SerialName("sportId")
    val sportId: Long,
    @SerialName("receiverProfileId")
    val receiverProfileId: String,
    @SerialName("gameId")
    val gameId: String,
    @SerialName("reviewId")
    val reviewId: String,
    @SerialName("reviewer")
    val reviewer: UserSummaryDto,
)
