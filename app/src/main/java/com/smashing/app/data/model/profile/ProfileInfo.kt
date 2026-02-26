package com.smashing.app.data.model.profile

import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

data class ProfileInfo(
    val profileId: String = "",
    val sportType: SportType = SportType.PING_PONG,
    val tierType: TierType = TierType.IRON,
    val lp: Int = 0,
    val minLp: Int = 0,
    val maxLp: Int = 1,
    val winCount: Int = 0,
    val loseCount: Int = 0,
)
