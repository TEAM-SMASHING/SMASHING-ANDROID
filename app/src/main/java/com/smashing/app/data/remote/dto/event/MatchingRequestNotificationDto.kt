package com.smashing.app.data.remote.dto.event

import com.smashing.app.data.remote.dto.event.common.UserSummaryDto
import com.smashing.app.data.type.NotificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchingRequestNotificationDto(
    @SerialName("type")
    val type: String,
    @SerialName("notificationId")
    val notificationId: String,
    @SerialName("notificationType")
    val notificationType: NotificationType,
    @SerialName("notificationCreatedAt")
    val notificationCreatedAt: String,
    @SerialName("matchingId")
    val matchingId: String,
    @SerialName("sportId")
    val sportId: Long,
    @SerialName("receiverProfileId")
    val receiverProfileId: String,
    @SerialName("requester")
    val requester: UserSummaryDto,
)
