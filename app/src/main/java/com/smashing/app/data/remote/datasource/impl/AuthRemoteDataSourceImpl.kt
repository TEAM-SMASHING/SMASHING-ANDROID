package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse
import com.smashing.app.data.remote.service.KakaoLoginService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val kakaoLoginService: KakaoLoginService,
) : AuthRemoteDataSource {
    override suspend fun postKakaoLogin(authorization: String): BaseResponse<PostKakaoLoginResponse> {
        return kakaoLoginService.postKakaoLogin(authorization = authorization)
    }
}
