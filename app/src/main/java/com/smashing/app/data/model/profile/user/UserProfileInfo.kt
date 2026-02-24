package com.smashing.app.data.model.profile.user

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.type.GenderType

data class UserProfileInfo(
    val nickname: String,
    val genderType: GenderType,
    val userProfileInfo: ProfileInfo,
    val reviewCount: Long,
    val isChallengeable: Boolean,
    val isAcceptable: Boolean,
    val receivedMatchingId: String?,
    val userProfileItem: List<ProfileItem>
)
