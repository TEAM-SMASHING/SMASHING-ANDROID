package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse
import retrofit2.http.GET

interface MyService {

    @GET("/api/v1/users/me/profiles/tier")
    suspend fun getMyTierProfile(): BaseResponse<GetMyTierProfileResponse>
}