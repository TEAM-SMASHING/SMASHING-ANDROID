package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toReviewDetail
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
}
