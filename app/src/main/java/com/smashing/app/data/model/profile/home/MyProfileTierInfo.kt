package com.smashing.app.data.model.profile.home

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem


data class MyProfileTierInfo(
    val nickname: String,
    val region: String,
    val myProfileInfo: ProfileInfo,
    val myProfileItem: List<ProfileItem>,
)
