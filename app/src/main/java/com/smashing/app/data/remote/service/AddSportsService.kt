package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.addsports.AddSportsProfileRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AddSportsService {
    @POST("/api/v1/users/me/profiles")
    suspend fun addSportProfile(
        @Body request: AddSportsProfileRequest
    ): BaseResponse<String?> // data가 null로 옴
}

