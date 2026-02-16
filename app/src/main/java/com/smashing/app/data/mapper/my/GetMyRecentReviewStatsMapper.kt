package com.smashing.app.data.mapper.my

import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.remote.dto.my.GetMyRecentReviewStatsResponse

fun GetMyRecentReviewStatsResponse.toGameReviewResult(): GameReviewResult =
    GameReviewResult(
        bestCount = ratingCounts.best,
        goodCount = ratingCounts.good,
        badCount = ratingCounts.bad,
        goodMannerCount = tagCounts.goodManner,
        onTimeCount = tagCounts.onTime,
        fairPlayCount = tagCounts.fairPlay,
        fastResponseCount = tagCounts.fastResponse,
    )
