package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.auth.GetNickNameAvailableResponse
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginResponse
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpResponse

interface AuthRemoteDataSource {
    suspend fun postKakaoLogin(request: PostKakaoLoginRequest): BaseResponse<PostKakaoLoginResponse>
    suspend fun postSignUp(request: PostSignUpRequest): BaseResponse<PostSignUpResponse>
    suspend fun getNicknameAvailable(nickname: String): BaseResponse<GetNickNameAvailableResponse>
}
