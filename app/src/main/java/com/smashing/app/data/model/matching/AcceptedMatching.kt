package com.smashing.app.data.model.matching

import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

data class AcceptedMatching(
    val gameId: String,
    val resultStatus: GameResultStatusType,
    val createdAt: String,
    val profileId: String,
    val nickname: String,
    val genderType: GenderType,
    val tierType: TierType,
    val latestSubmissionId: String?,
    val latestAttemptNo: Int?,
    val latestSubmitterProfileId: String?,
)
