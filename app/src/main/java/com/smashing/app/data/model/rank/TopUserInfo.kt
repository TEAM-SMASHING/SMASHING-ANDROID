package com.smashing.app.data.model.rank

import com.smashing.app.core.common.type.TierType

data class TopUserInfo (
    val rank: Int,
    val userId: String,
    val nickname: String,
    val tier: TierType,
    val lp: Int,
)