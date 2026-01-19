package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.profile.my.MyPageData
import retrofit2.http.GET

interface MyService {
    @GET("/api/v1/users/me/profiles")
    suspend fun getMyProfile(): BaseResponse<MyPageData>
}
