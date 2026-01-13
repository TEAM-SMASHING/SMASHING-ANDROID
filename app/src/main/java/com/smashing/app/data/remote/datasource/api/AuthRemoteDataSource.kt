package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse
import com.smashing.app.data.remote.dto.PostSignUpRequest
import com.smashing.app.data.remote.dto.PostSignUpResponse

interface AuthRemoteDataSource {
    suspend fun postKakaoLogin(request: PostKakaoLoginRequest): BaseResponse<PostKakaoLoginResponse>
    suspend fun postSignUp(request: PostSignUpRequest): BaseResponse<PostSignUpResponse>
}
