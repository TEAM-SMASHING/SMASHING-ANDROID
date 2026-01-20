package com.smashing.app.data.model.profile

import com.smashing.app.data.type.SportType


data class SportsProfile(
    val profileId: String = "",
    val sportType: SportType = SportType.TENNIS,
    val isActive: Boolean = false,
)
