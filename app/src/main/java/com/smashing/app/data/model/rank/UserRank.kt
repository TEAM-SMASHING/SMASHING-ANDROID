package com.smashing.app.data.model.rank

import com.smashing.app.data.type.TierType

data class UserRank (
    val rank: Int = 0,
    val userProfileId: String = "",
    val nickname: String,
    val tier: TierType,
    val lp: Int,
)
