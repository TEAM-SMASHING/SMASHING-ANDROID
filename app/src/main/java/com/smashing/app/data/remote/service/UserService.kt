package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.user.GetUserRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/api/v1/users/{userId}/profiles")
    suspend fun getUserInfoDetail(
        @Path ("userId")
        userId: String,
        @Query ("sportCode")
        sportCode: String?,
    ): BaseResponse<GetUserInfoDetailResponse>

    @GET("/api/v1/users/{userId}/reviews/summary")
    suspend fun getUserRecentReviewStats(
        @Path("userId")
        userId: String,
        @Query("sportCode")
        sportCode: String?,
    ): BaseResponse<GetUserRecentReviewStatsResponse>
}
