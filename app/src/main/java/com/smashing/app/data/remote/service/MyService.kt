package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.AddSportProfileRequest
import com.smashing.app.data.remote.dto.my.MyPageData
import com.smashing.app.data.remote.dto.my.MyProfileReviewListData
import com.smashing.app.data.remote.dto.my.MyProfileSwitchRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    suspend fun putActiveMyProfile(
        @Body request: MyProfileSwitchRequest
    ): BaseResponse<Unit?>

    @POST("/api/v1/users/me/profiles")
    suspend fun addSportProfile(
        @Body request: AddSportProfileRequest
    ): BaseResponse<Unit?>

    @GET("/api/v1/users/me/profiles/tier")
    suspend fun getMyTierProfile(): BaseResponse<GetMyTierProfileResponse>
}