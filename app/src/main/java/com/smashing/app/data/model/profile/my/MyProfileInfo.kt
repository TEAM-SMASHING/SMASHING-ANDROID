package com.smashing.app.data.model.profile.my

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.type.GenderType

data class MyProfileInfo(
    val nickname: String,
    val genderType: GenderType,
    val myProfileInfo: ProfileInfo,
    val reviewCount:Long,
    val myProfileItem: List<ProfileItem>,
)
data class MyProfileTierInfo(
    val nickname: String,
    val region: String,
    val myProfileInfo: ProfileInfo,
    val myProfileItem: List<ProfileItem>,
)
