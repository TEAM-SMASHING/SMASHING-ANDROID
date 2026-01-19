package com.smashing.app.data.mapper

import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.remote.dto.profile.my.AllProfileDto
import com.smashing.app.data.remote.dto.profile.my.MyPageData
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.SportType.Companion.findSportTypeToSportName
import com.smashing.app.data.type.TierType

fun MyPageData.toProfileInfo(): ProfileInfo {
    val active = this.activeProfile

    return ProfileInfo(
        profileId = active.profileId,
        sportType = findSportTypeToSportName(active.sportCode),
        tierType = TierType.findTierType(active.tierCode),
        lp = active.lp,
        minLp = active.minLp,
        maxLp = active.maxLp,
        winCount = active.wins,
        loseCount = active.losses,
        reviewCount = active.reviews,

        nickname = this.nickname,
        genderType = GenderType.findByName(this.gender)
    )
}

fun AllProfileDto.toSportProfile(): SportProfile {
    return SportProfile(
        profileId = this.profileId,
        sportType = SportType.findSportTypeToSportCode(this.sportCode),
        isActive = this.isActive
    )
}

fun MyPageData.toMyPageInfo(): MyPageInfo {
    return MyPageInfo(
        profileInfo = this.toProfileInfo(),
        sportProfiles = this.allProfiles.map { it.toSportProfile() }
    )
}
