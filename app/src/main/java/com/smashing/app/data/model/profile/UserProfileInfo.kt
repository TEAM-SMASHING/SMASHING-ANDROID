package com.smashing.app.data.model.profile

import com.smashing.app.core.common.type.SportType
import com.smashing.app.core.common.type.TierType
import java.time.LocalDateTime

data class UserProfileInfo(
    val tierType: TierType,
    val tierIconResId: Int,
    val mySports: List<SportType>,
    val selectedSport: SportType,

    val lpProgress: Float,
    val minLp: Int,
    val maxLp: Int,
    val lp: Int = maxLp - minLp,

    val winCount: Int,
    val loseCount: Int,
)

data class Rating(
    val best: Int,
    val good: Int,
    val bad: Int,
)


data class Review(
    val gameId: String,
    val reviewId: String,
    val opponentNickname: String,
    val confirmedAt: LocalDateTime,
    val content: String?,
)

data class TagCount(
    val onTime:Int,
    val goodManner:Int,
    val fairPlay:Int,
    val fastResponse:Int,
)
