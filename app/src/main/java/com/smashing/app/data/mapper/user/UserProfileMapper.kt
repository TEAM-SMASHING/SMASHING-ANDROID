package com.smashing.app.data.mapper.user

import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.ProfileItem
import com.smashing.app.data.model.profile.user.UserProfileInfo
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import com.smashing.app.data.remote.dto.user.GetUserRecentReviewStatsResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

fun GetUserInfoDetailResponse.toUserProfileInfo(): UserProfileInfo =
     UserProfileInfo(
        nickname = this.nickname,
        genderType = GenderType.findByName(this.gender),
        userProfileInfo = this.selectedProfile.toSelectedProfile(),
        reviewCount = this.selectedProfile.reviews,
        userProfileItem = this.allProfiles.map { it.toUserProfileItem()},
        isChallengeable = this.isChallengeable,
        isAcceptable = this.isAcceptable,
        receivedMatchingId = this.receivedMatchingId,
    )


fun GetUserInfoDetailResponse.SelectedProfile.toSelectedProfile(): ProfileInfo {
    return ProfileInfo(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(sportCode),
        tierType = TierType.findTierType(tierCode),
        lp = lp,
        minLp = minLp,
        maxLp = maxLp,
        winCount = wins,
        loseCount =losses,
    )
}

fun GetUserInfoDetailResponse.Profile.toUserProfileItem(): ProfileItem =
    ProfileItem(
        profileId = profileId,
        sportType = SportType.findSportTypeToSportCode(this.sportCode),
        isActive = isSelected,
    )

fun GetUserRecentReviewStatsResponse.toGameReviewResult(): GameReviewResult =
    GameReviewResult(
        bestCount = this.ratingCounts.best,
        goodCount = this.ratingCounts.good,
        badCount = this.ratingCounts.bad,
        goodMannerCount = this.tagCounts.goodManner,
        onTimeCount = this.tagCounts.onTime,
        fairPlayCount = this.tagCounts.fairPlay,
        fastResponseCount = this.tagCounts.fastResponse,
    )

