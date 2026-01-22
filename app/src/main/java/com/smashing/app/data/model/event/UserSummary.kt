package com.smashing.app.data.model.event

import com.smashing.app.data.type.TierType

data class UserSummary(
    val userId: String,
    val nickname: String,
    val tierType: TierType,
)
