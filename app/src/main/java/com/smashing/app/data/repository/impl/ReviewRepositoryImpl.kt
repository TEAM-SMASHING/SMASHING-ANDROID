package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.review.toGameReviewList
import com.smashing.app.data.mapper.review.toReviewDetail
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
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
        userProfileId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): Result<CursorPage<GameReview>> =
        suspendRunCatching {
            reviewRemoteDataSource.getUserRecentReviewList(
                userProfileId = userProfileId,
                sportCode = sportCode,
                cursor = cursor,
                size = size,
                snapshotAt = snapshotAt,
            ).requireData().toGameReviewList()
        }

    override suspend fun getMyRecentReviewList(
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): Result<CursorPage<GameReview>> =
        suspendRunCatching {
            reviewRemoteDataSource.getMyRecentReviewList(
                cursor = cursor,
                size = size,
                snapshotAt = snapshotAt,
            ).requireData().toGameReviewList()
        }
}
