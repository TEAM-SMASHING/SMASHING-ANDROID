package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse
import com.smashing.app.data.remote.dto.PostSignUpRequest
import com.smashing.app.data.remote.dto.PostSignUpResponse
import com.smashing.app.data.remote.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun postKakaoLogin(authorization: String): BaseResponse<PostKakaoLoginResponse> {
        // Todo: 서버 배포 후 수정 필요
        //return authService.postKakaoLogin(authorization = authorization)
        return BaseResponse(
            status = "success_mock",
            statusCode = 200,
            data = PostKakaoLoginResponse(
                accessToken = "fake_access_token",
                refreshToken = "fake_refresh_token",
                authId = "fake_authId"
            ),
            timestamp = "",
        )
    }

    override suspend fun postSignUp(request: PostSignUpRequest): BaseResponse<PostSignUpResponse> {
        // Todo: 서버 배포 후 수정 필요
        //return authService.postSignUp(request = request)
        return BaseResponse(
            status = "success_mock",
            statusCode = 200,
            data = PostSignUpResponse(
                accessToken = "fake_access_token_signup",
                refreshToken = "fake_refresh_token_signup",
                authId = "fake_authId_signup"
            ),
            timestamp = "",
        )
    }
}
