package com.smashing.app.data.mapper.my

import com.smashing.app.data.model.profile.ActiveUserProfile
import com.smashing.app.data.model.profile.UserProfile
import com.smashing.app.data.model.profile.UserProfileItem
import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.AddSportsInfo
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.AllProfileDto
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse
import com.smashing.app.data.remote.dto.my.MyPageData
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.SportType.Companion.findSportTypeToSportCode
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

fun MyPageData.toProfileInfo(): ProfileInfo {
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
        reviewCount = active.reviews,
        nickname = this.nickname,
        genderType = GenderType.findByName(this.gender)
    )
}

fun AllProfileDto.toSportProfile(): SportProfile {
    return SportProfile(
        profileId = this.profileId,
        sportType = findSportTypeToSportCode(this.sportCode),
        isActive = this.isActive
    )
}

fun MyPageData.toMyPageInfo(): MyPageInfo {
    return MyPageInfo(
        profileInfo = this.toProfileInfo(),
        sportProfiles = this.allProfiles.map { it.toSportProfile() }
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

fun GetMyRecentReviewStatsResponse.toGameReviewResult(): GameReviewResult =
    GameReviewResult(
        bestCount = this.ratingCounts.best,
        goodCount = this.ratingCounts.good,
        badCount = this.ratingCounts.bad,
        goodMannerCount = this.tagCounts.goodManner,
        onTimeCount = this.tagCounts.onTime,
        fairPlayCount = this.tagCounts.fairPlay,
        fastResponseCount = this.tagCounts.fastResponse,
    )


