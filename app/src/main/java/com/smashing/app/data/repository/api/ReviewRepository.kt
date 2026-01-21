package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult

interface ReviewRepository {
    suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): Result<CursorPage<GameReview>>


    suspend fun getMyGameReviews(
        cursor: String?,
        size: Int?,
    ): Result<CursorPage<GameReview>>

    suspend fun getUserRecentReviewStats(
    ): Result<GameReviewResult>

}
