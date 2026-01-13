package com.smashing.app.data.model.matching

import com.smashing.app.core.common.type.GenderType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType

data class ReceivedMatching(
    val matchingId: String,
    val userId: String,
    val nickname: String,
    val genderType: GenderType,
    val tierType: TierType,
    val reviewCount: Long,
    val winCount: Int,
    val loseCount: Int,
)
