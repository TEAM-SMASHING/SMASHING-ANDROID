package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse
import com.smashing.app.data.remote.dto.review.MyProfileReviewListData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

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
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    @GET("/api/v1/users/me/reviews/recent")
    suspend fun getMyGameReviewsResponse(
        @Query("cursor") cursor: String?,
        @Query("size") size: Int?,
    ): BaseResponse<MyProfileReviewListData>

    @GET("/api/v1/users/me/reviews/summary")
    suspend fun getMyRecentReviewStats(): BaseResponse<GetMyRecentReviewStatsResponse>


}
