package com.smashing.app.data.repository.api

import com.smashing.app.data.model.review.ReviewDetail

interface ReviewRepository {

    suspend fun getReview(
        reviewId: String,
    ): Result<ReviewDetail>
}
