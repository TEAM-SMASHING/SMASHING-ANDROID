package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetUserRecentListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewService {

    @GET("/api/v1/users/me/reviews/recent")
    suspend fun getUserRecentList(
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Int?,
    ): BaseResponse<CursorDto<GetUserRecentListResponse>>
}
