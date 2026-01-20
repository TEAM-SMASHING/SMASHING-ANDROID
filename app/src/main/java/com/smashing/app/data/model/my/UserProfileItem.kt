package com.smashing.app.data.model.my

import com.smashing.app.data.type.SportType

data class UserProfileItem(
    val profileId: String,
    val sportCode: SportType,
    val isActive: Boolean,
)