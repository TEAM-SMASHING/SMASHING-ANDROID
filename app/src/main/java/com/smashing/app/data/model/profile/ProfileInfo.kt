package com.smashing.app.data.model.profile

import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

data class MyPageInfo(
    val profileInfo: ProfileInfo,
    val sportsProfiles: List<SportsProfile>,
)

data class ProfileInfo(
    val profileId: String = "",
    val sportType: SportType = SportType.TENNIS,
    val nickname: String = "",
    val genderType: GenderType = GenderType.MALE,
    val tierType: TierType = TierType.GOLD_1,
    val lp: Int = 0,
    val minLp: Int = 0,
    val maxLp: Int = 1,
    val winCount: Int = 0,
    val loseCount: Int = 0,
    val reviewCount: Long = 0,
)
