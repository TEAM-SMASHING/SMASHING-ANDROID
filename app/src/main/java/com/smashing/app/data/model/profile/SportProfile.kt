package com.smashing.app.data.model.profile

import com.smashing.app.data.type.SportType


data class SportProfile(
    val profileId: String,
    val sportType: SportType,
    val isActive: Boolean,
)
