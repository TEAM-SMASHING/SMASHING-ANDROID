package com.smashing.app.data.model.matching

import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType
import java.time.OffsetDateTime

data class AcceptedMatching(
    val matchingId: String,
    val gameId: String,
    val userId: String,
    val nickname: String,
    val genderType: GenderType,
    val tierType: TierType,
    val openChatUrl: String,
    val isResultBannerBlocked: Boolean,
    val cooldownUntil: OffsetDateTime,
)
