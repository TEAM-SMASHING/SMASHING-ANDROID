package com.smashing.app.data.mapper

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.model.profile.SportProfile
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.remote.dto.my.AllProfileDto
import com.smashing.app.data.remote.dto.my.MyPageData
import com.smashing.app.data.remote.dto.my.MyProfileReviewListData
import com.smashing.app.data.remote.dto.my.MyReviewsDto
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.SportType.Companion.findSportTypeToSportCode
import com.smashing.app.data.type.TierType

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


fun MyProfileReviewListData.toGameReviewPage(): CursorPage<GameReview> {
    return CursorPage(
        items = this.results.map { it.toGameReview() },
        cursor = Cursor(
            snapshotAt = this.snapshotAt,
            nextCursor = this.nextCursor,
            hasNext = this.hasNext
        )
    )
}

fun MyReviewsDto.toGameReview(): GameReview {
    return GameReview(
        gameReviewId = this.gameReviewId,
        opponentNickname = this.nickname,
        createdAt = this.createdAt,
        content = this.content
    )
}
