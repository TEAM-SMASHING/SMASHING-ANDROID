package com.smashing.app.data.model.profile

import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

//TODO nickname과 region은 서버에서 추가 예정
data class ActiveUserProfile(
    val nickname: String,
    val region: String,
    val profileId: String,
    val sportType: SportType,
    val tierType: TierType,
    val lp: Int,
    val minLp: Int,
    val maxLp: Int,
    val wins: Int,
    val losses: Int,
)
