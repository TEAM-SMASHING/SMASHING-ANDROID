package com.smashing.app.data.model.rank

import com.smashing.app.data.type.TierType

data class UserRank(
    val userId: String,
    val nickname: String,
    val rank: Int,
    val tierType: TierType,
    val lp: Int,
)