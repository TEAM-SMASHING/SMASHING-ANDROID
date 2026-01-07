package com.smashing.app.data.model.matching

import com.smashing.app.core.common.type.TierType

data class RequesterSummary(
    val userId: String,
    val nickname: String,
    val gender: String,
    val tierType: TierType?,
    val winCount: Int,
    val loseCount: Int,
    val reviewCount: Int,
)
