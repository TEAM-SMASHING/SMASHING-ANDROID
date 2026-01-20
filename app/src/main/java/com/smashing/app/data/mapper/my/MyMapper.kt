package com.smashing.app.data.mapper.my

import com.smashing.app.data.model.my.ActiveUserProfile
import com.smashing.app.data.model.my.UserProfile
import com.smashing.app.data.model.my.UserProfileItem
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

fun GetMyTierProfileResponse.toUserProfile(): UserProfile =
    UserProfile(
        activeUserProfile = this.toActiveUserProfile(),
        allProfiles = allProfiles.map { it.toUserProfileItem() },
    )

private fun GetMyTierProfileResponse.toActiveUserProfile(): ActiveUserProfile = ActiveUserProfile(
    nickname = nickname,
    region = region,
    profileId = activeProfile.profileId,
    sportType = SportType.findSportTypeToSportCode(activeProfile.sportCode),
    tierType = TierType.findTierType(activeProfile.tierCode),
    lp = activeProfile.lp,
    minLp = activeProfile.minLp,
    maxLp = activeProfile.maxLp,
    wins = activeProfile.wins,
    losses = activeProfile.losses,
)

private fun GetMyTierProfileResponse.ProfileItemResponse.toUserProfileItem(): UserProfileItem =
    UserProfileItem(
        profileId = profileId,
        sportCode = SportType.findSportTypeToSportCode(sportCode),
        isActive = isActive,
    )