package com.smashing.app.data.model.event

import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

data class Requester(
    val userId: String,
    val nickname: String,
    val genderType: GenderType,
    val tierType: TierType,
    val winCount: Int,
    val loseCount: Int,
    val reviewCount: Long,
)
