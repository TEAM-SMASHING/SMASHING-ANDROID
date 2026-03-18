package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.auth.GetNickNameAvailableResponse
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginResponse
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidRequest
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidResponse
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpResponse
import com.smashing.app.data.remote.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun postKakaoLogin(request: PostKakaoLoginRequest): BaseResponse<PostKakaoLoginResponse> {
        return authService.postKakaoLogin(request = request)
    }

    override suspend fun postSignUp(request: PostSignUpRequest): BaseResponse<PostSignUpResponse> {
        return authService.postSignUp(request = request)
    }

    override suspend fun getNicknameAvailable(nickname: String): BaseResponse<GetNickNameAvailableResponse> {
        return authService.getNickNameAvailable(nickname = nickname)
    }

    override suspend fun postOpenchatValid(request: PostOpenchatValidRequest): BaseResponse<PostOpenchatValidResponse> {
        return authService.postOpenchatValid(request = request)
    }

    override suspend fun postLogout(token: String): BaseResponse<Unit> {
        return authService.postLogout(token = token)
    }
}
