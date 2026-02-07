package com.smashing.app.data.model.profile.my

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem

data class MyProfileInfo(
    val nickname: String,
    val region: String,
    val myProfileInfo: ProfileInfo,
    val myProfileItem: List<ProfileItem>,
)
