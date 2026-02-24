package com.smashing.app.data.mapper.my

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.profile.home.MyProfileTierInfo
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

fun GetMyTierProfileResponse.toMyProfileTierInfo(): MyProfileTierInfo {
    return MyProfileTierInfo(
        nickname = nickname,
        region = region,
        myProfileInfo = toProfileInfo(),
        myProfileItem = allProfiles.map { it.toProfileItem() },
    )
}


private fun GetMyTierProfileResponse.toProfileInfo(): ProfileInfo {
    val active = this.activeProfile
    return ProfileInfo(
        profileId = active.profileId,
        sportType = SportType.findSportTypeToSportCode(active.sportCode),
        tierType = TierType.findTierType(active.tierCode),
        lp = active.lp,
        minLp = active.minLp,
        maxLp = active.maxLp,
        winCount = active.wins,
        loseCount = active.losses,
    )
}

private fun GetMyTierProfileResponse.MyProfileItemResponse.toProfileItem(): ProfileItem =
    ProfileItem(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(sportCode),
        isActive = isActive,
    )
