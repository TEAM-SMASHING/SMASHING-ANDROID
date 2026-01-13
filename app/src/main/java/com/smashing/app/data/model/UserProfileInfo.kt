package com.smashing.app.data.model

import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType

data class UserProfileInfo(
    val tierType: TierType,
    val tierIconResId: Int,
    val mySports: List<SportType>,
    val selectedSport: SportType,

    val lpProgress: Float,
    val lpStatusText: String,
    val totalLpText: String,

    val winCount: Int,
    val loseCount: Int,
    val winRate: String,
)
