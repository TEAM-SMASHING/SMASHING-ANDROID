package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse
import com.smashing.app.data.remote.dto.PostSignUpRequest
import com.smashing.app.data.remote.dto.PostSignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login/kakao")
    suspend fun postKakaoLogin(
        @Body request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    @POST("/api/v1/auth/signup")
    suspend fun postSignUp(
        @Body request: PostSignUpRequest,
    ): BaseResponse<PostSignUpResponse>
}
