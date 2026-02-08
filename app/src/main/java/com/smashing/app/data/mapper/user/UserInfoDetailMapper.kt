package com.smashing.app.data.mapper.user

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.profile.user.UserProfileInfo
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

fun GetUserInfoDetailResponse.toProfileInfo(): UserProfileInfo =
    UserProfileInfo(
        profileInfo = ProfileInfo(
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
        ),
        isChallengeable = this.isChallengeable,
        isAcceptable = this.isAcceptable,
        receivedMatchingId = this.receivedMatchingId,
        sportProfile = allProfiles.map { it.toSportProfile() },
    )


fun GetUserInfoDetailResponse.Profile.toSportProfile(): ProfileItem =
    ProfileItem(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(this.sportCode),
        isActive = isSelected,
    )

