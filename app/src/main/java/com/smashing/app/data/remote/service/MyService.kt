package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.profile.my.MyPageData
import com.smashing.app.data.remote.dto.profile.my.MyProfileReviewListData
import com.smashing.app.data.remote.dto.profile.my.MyProfileSwitchRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface MyService {
    @GET("/api/v1/users/me/profiles")
    suspend fun getMyProfile(): BaseResponse<MyPageData>

    @GET("/api/v1/users/me/reviews/recent")
    suspend fun getMyGameReviews(
        @Query("cursor") cursor: String?,
        @Query("size") size: Int?,
    ): BaseResponse<MyProfileReviewListData>

    @PUT("/api/v1/users/me/active-profile")
    suspend fun switchActiveMyProfile(
      @Body request: MyProfileSwitchRequest
    ): BaseResponse<Unit?>
}
