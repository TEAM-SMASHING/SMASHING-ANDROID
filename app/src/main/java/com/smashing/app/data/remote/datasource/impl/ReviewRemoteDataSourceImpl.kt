package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.review.GetReviewResponse
import com.smashing.app.data.remote.service.ReviewService
import javax.inject.Inject

class ReviewRemoteDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService,
) : ReviewRemoteDataSource {

    override suspend fun getReview(
        reviewId: String,
    ): BaseResponse<GetReviewResponse> =
        reviewService.getReview(reviewId = reviewId)
}
