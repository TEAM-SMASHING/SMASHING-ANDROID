package com.smashing.app.data.mapper.user

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.profile.user.UserProfileInfo
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

fun GetUserInfoDetailResponse.toUserProfileInfo(): UserProfileInfo =
    UserProfileInfo(
        nickname = nickname,
        genderType = GenderType.findByName(gender),
        userProfileInfo = selectedProfile.toSelectedProfile(),
        reviewCount = selectedProfile.reviews,
        userProfileItem = allProfiles.map { it.toUserProfileItem() },
        isChallengeable = isChallengeable,
        isAcceptable = isAcceptable,
        receivedMatchingId = receivedMatchingId,
    )


private fun GetUserInfoDetailResponse.SelectedProfile.toSelectedProfile(): ProfileInfo {
    return ProfileInfo(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(sportCode),
        tierType = TierType.findTierType(tierCode),
        lp = lp,
        minLp = minLp,
        maxLp = maxLp,
        winCount = wins,
        loseCount = losses,
    )
}

private fun GetUserInfoDetailResponse.Profile.toUserProfileItem(): ProfileItem =
    ProfileItem(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(this.sportCode),
        isActive = isSelected,
    )
