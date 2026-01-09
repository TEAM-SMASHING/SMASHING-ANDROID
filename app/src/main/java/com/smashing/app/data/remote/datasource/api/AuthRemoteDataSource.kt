package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.PostKakaoLoginResponse

interface AuthRemoteDataSource {
    suspend fun postKakaoLogin(authorization: String): BaseResponse<PostKakaoLoginResponse>
}
