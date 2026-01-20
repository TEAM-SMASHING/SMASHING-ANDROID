package com.smashing.app.data.mapper.user

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType


fun GetUserInfoDetailResponse.toProfileInfo(): ProfileInfo =
    ProfileInfo(
        profileId = selectedProfile.profileId,
        sportType = SportType.findSportTypeToSportCode(selectedProfile.sportCode),
        nickname = this.nickname,
        genderType = GenderType.findByName(this.gender),
        tierType = TierType.findTierType(selectedProfile.tierCode),
        lp = selectedProfile.lp,
        minLp = selectedProfile.minLp,
        maxLp = selectedProfile.maxLp,
        winCount = selectedProfile.wins,
        loseCount = selectedProfile.losses,
        reviewCount = selectedProfile.reviews,
    )

fun GetUserInfoDetailResponse.Profile.toSportProfile(): SportProfile =
    SportProfile(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(this.sportCode),
        isActive = isSelected,
    )

fun GetUserInfoDetailResponse.toAllProfiles(): List<SportProfile> =
    allProfiles.map { it.toSportProfile() }
