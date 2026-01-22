package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.model.review.ReviewDetail

interface ReviewRepository {
    suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): Result<CursorPage<GameReview>>

    suspend fun getReview(
        reviewId: String,
    ): Result<ReviewDetail>

    suspend fun getMyGameReviews(
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): Result<CursorPage<GameReview>>

    suspend fun getUserRecentReviewStats(
    ): Result<GameReviewResult>

}
