package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse
import com.smashing.app.data.remote.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val kakaoLoginService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun postKakaoLogin(authorization: String): BaseResponse<PostKakaoLoginResponse> {
        // Todo: 서버 배포 후 수정 필요
        //return kakaoLoginService.postKakaoLogin(authorization = authorization)
        return BaseResponse(
            status = "success_mock",
            statusCode = 200,
            data = PostKakaoLoginResponse(
                accessToken = "fake_access_token",
                refreshToken = "fake_refresh_token"
            ),
            timestamp = "",
        )
    }
}
