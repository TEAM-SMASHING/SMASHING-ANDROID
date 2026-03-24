package com.smashing.app.data.model.notification

import com.smashing.app.data.type.SportType

data class NotificationSportMatch(
    val receiverUserProfileId: String,
    val isMatch: Boolean,
    val receiverSportType: SportType,
)
