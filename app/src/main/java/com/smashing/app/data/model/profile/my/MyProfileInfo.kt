package com.smashing.app.data.model.profile.my

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.type.GenderType
import kotlinx.collections.immutable.persistentListOf

data class MyProfileInfo(
    val nickname: String = "",
    val genderType: GenderType = GenderType.MALE,
    val myProfileInfo: ProfileInfo = ProfileInfo(),
    val reviewCount: Long = 0L,
    val myProfileItem: List<ProfileItem> = persistentListOf(),
)
