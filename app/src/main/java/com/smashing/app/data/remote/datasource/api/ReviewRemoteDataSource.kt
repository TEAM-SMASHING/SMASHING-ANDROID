package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.review.GetReviewResponse

interface ReviewRemoteDataSource {

    suspend fun getReview(
        reviewId: String,
    ): BaseResponse<GetReviewResponse>
}
