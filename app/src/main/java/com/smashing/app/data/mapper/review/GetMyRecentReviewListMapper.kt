package com.smashing.app.data.mapper.review

import com.smashing.app.core.util.ConvertTimeProvider.convertLocalDateTimeToTime
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.remote.dto.review.GetMyReviewResultsResponse

fun GetMyReviewResultsResponse.toGameReview(): GameReview {
    return GameReview(
        gameReviewId = this.gameReviewId,
        opponentNickname = this.nickname,
        createdAt = convertLocalDateTimeToTime(this.createdAt),
        content = this.content
    )
}
