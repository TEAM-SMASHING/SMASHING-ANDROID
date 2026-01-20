package com.smashing.app.data.mapper.user

import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.remote.dto.user.GetUserRecentReviewStatsResponse

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
