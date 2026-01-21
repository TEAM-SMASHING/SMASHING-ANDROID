package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.review.GetReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewService {

    @GET("/api/v1/reviews/{reviewId}")
    suspend fun getReview(
        @Path("reviewId")
        reviewId: String,
    ): BaseResponse<GetReviewResponse>
}
