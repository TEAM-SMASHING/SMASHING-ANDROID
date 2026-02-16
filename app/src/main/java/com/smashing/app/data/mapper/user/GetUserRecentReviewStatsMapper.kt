package com.smashing.app.data.mapper.user

import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.remote.dto.user.GetUserRecentReviewStatsResponse

fun GetUserRecentReviewStatsResponse.toGameReviewResult(): GameReviewResult =
    GameReviewResult(
        bestCount = ratingCounts.best,
        goodCount = ratingCounts.good,
        badCount = ratingCounts.bad,
        goodMannerCount = tagCounts.goodManner,
        onTimeCount = tagCounts.onTime,
        fairPlayCount = tagCounts.fairPlay,
        fastResponseCount = tagCounts.fastResponse,
    )

