package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import retrofit2.http.GET

interface UserService {

    @GET("/api/v1/users/{userId}/profiles?")
    suspend fun getUserInfoDetail(): BaseResponse<GetUserInfoDetailResponse>
}
