package com.smashing.app.data.model.profile

import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

data class MyPageInfo(
    val profileInfo: ProfileInfo,
    val sportProfiles: List<ProfileItem>,
)

data class ProfileInfo(
    val profileId: String,
    val sportType: SportType,
    val nickname: String,
    val genderType: GenderType,
    val tierType: TierType,
    val lp: Int,
    val minLp: Int,
    val maxLp: Int,
    val winCount: Int,
    val loseCount: Int,
    val reviewCount: Long,
)
