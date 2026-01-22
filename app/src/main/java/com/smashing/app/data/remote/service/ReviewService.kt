package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.review.GetReviewResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetMyGameReviewsResponse
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

    @GET("/api/v1/reviews/{reviewId}")
    suspend fun getReview(
        @Path("reviewId")
        reviewId: String,
    ): BaseResponse<GetReviewResponse>

    @GET("/api/v1/users/{userId}/reviews/recent")
    suspend fun getUserRecentReviewList(
        @Path("userId")
        userId: String,
        @Query("sportCode")
        sportCode: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Int?,
        @Query("snapshotAt")
        snapshotAt: String?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    @GET("/api/v1/users/me/reviews/recent")
    suspend fun getMyGameReviews(
        @Query("cursor") cursor: String?,
        @Query("size") size: Int?,
        @Query("snapshotAt") snapshotAt: String?,
    ): BaseResponse<GetMyGameReviewsResponse>

    @GET("/api/v1/users/me/reviews/summary")
    suspend fun getMyRecentReviewStats(): BaseResponse<GetMyRecentReviewStatsResponse>


}
