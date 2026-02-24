package com.smashing.app.data.model.profile.user

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.type.GenderType
import kotlinx.collections.immutable.persistentListOf

data class UserProfileInfo(
    val nickname: String = "",
    val genderType: GenderType = GenderType.MALE,
    val userProfileInfo: ProfileInfo = ProfileInfo(),
    val reviewCount: Long = 0L,
    val isChallengeable: Boolean = false,
    val isAcceptable: Boolean = false,
    val receivedMatchingId: String? = null,
    val userProfileItem: List<ProfileItem> = persistentListOf(),
)

