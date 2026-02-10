package com.smashing.app.data.mapper.my

import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.profile.my.MyProfileInfo
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.GetMyProfileResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType.Companion.findSportTypeToSportCode
import com.smashing.app.data.type.TierType


fun GetMyProfileResponse.toMyProfileInfo(): MyProfileInfo {
    return MyProfileInfo(
        nickname = this.nickname,
        genderType = GenderType.findByName(this.gender),
        myProfileInfo = this.toProfileInfo(),
        reviewCount = this.activeProfile.reviews,
        myProfileItem = this.allProfiles.map { it.toProfileItem() },
    )
}

private fun GetMyProfileResponse.toProfileInfo(): ProfileInfo {
    val active = this.activeProfile
    return ProfileInfo(
        profileId = active.profileId,
        sportType = findSportTypeToSportCode(active.sportCode),
        tierType = TierType.findTierType(active.tierCode),
        lp = active.lp,
        minLp = active.minLp,
        maxLp = active.maxLp,
        winCount = active.wins,
        loseCount = active.losses,
    )
}

private fun GetMyProfileResponse.MyProfileItemResponse.toProfileItem(): ProfileItem {
    return ProfileItem(
        profileId = this.profileId,
        sportType = findSportTypeToSportCode(this.sportCode),
        isActive = this.isActive,
    )
}

fun AddSportsInfo.toRequest():
        AddSportProfileRequest {
    val sportCode = requireNotNull(this.selectedSports?.code) { "sportCode is required" }
    return AddSportProfileRequest(
        sportCode = sportCode,
        experienceRange = this.selectedSkill?.skillCode ?: "LT_3_MONTHS",
    )
}

