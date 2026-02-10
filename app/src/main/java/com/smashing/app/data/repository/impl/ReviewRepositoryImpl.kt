package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.my.toGameReviewResult
import com.smashing.app.data.mapper.review.toGameReviewList
import com.smashing.app.data.mapper.review.toReviewDetail
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.model.review.ReviewDetail
import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewRemoteDataSource: ReviewRemoteDataSource,
) : ReviewRepository {

    override suspend fun getReview(
        reviewId: String,
    ): Result<ReviewDetail> = suspendRunCatching {
        reviewRemoteDataSource.getReview(
            reviewId = reviewId,
        ).requireData().toReviewDetail()
    }

    override suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?
    ): Result<CursorPage<GameReview>> =
        suspendRunCatching {
            reviewRemoteDataSource.getUserRecentReviewList(
                userId = userId,
                sportCode = sportCode,
                cursor = cursor,
                size = size,
            ).requireData().toGameReviewList()
        }

    override suspend fun getMyRecentReviewList(
        cursor: String?,
        size: Int?
    ): Result<CursorPage<GameReview>> =
        suspendRunCatching {
        reviewRemoteDataSource.getMyRecentReviewList(
            cursor = cursor,
            size = size
        ).requireData().toGameReviewList()
    }

    override suspend fun getUserRecentReviewStats(
    ): Result<GameReviewResult> = suspendRunCatching {
        reviewRemoteDataSource.getMyRecentReviewStats()
            .requireData()
            .toGameReviewResult()
    }
}
