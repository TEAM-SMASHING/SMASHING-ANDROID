package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.auth.GetNickNameAvailableResponse
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginResponse
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidRequest
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidResponse
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("/api/v1/auth/login/kakao")
    suspend fun postKakaoLogin(
        @Body request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    @POST("/api/v1/auth/signup")
    suspend fun postSignUp(
        @Body request: PostSignUpRequest,
    ): BaseResponse<PostSignUpResponse>

    @GET("/api/v1/users/nickname-availability")
    suspend fun getNickNameAvailable(
        @Query("nickname") nickname: String,
    ): BaseResponse<GetNickNameAvailableResponse>

    @POST("/api/v1/users/openchat/validate")
    suspend fun postOpenchatValid(
        @Body request: PostOpenchatValidRequest,
    ): BaseResponse<PostOpenchatValidResponse>

    @POST("/api/v1/auth/logout")
    suspend fun postLogout(
        @Header("Authorization") token: String,
    ): BaseResponse<Unit>
}
