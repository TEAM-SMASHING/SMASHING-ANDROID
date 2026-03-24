package com.smashing.app.data.remote.dto.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetNotificationSportMatchResponse(
    @SerialName("receiverUserProfileId")
    val receiverUserProfileId: String,
    @SerialName("isMatch")
    val isMatch: Boolean,
    @SerialName("notificationSportCode")
    val notificationSportCode: String,
)
