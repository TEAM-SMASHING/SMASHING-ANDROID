package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login/kakao")
    suspend fun postKakaoLogin(
        @Header("Authorization")
        authorization: String,
    ): BaseResponse<PostKakaoLoginResponse>
}
