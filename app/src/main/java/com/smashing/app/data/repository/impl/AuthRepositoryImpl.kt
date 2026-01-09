package com.smashing.app.data.repository.impl

import android.content.Context
import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toKakaoLoginToken
import com.smashing.app.data.model.auth.KakaoLoginToken
import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.datasource.api.KakaoAuthDataSource
import com.smashing.app.data.repository.api.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val kakaoAuthDataSource: KakaoAuthDataSource,
) : AuthRepository {

    override suspend fun loginKakao(context: Context): Result<String> =
        kakaoAuthDataSource.loginKakao(context)

    override suspend fun postKakaoLogin(authorization: String): Result<KakaoLoginToken> =
        suspendRunCatching {
            val response = authRemoteDataSource.postKakaoLogin(authorization)
            response.data?.toKakaoLoginToken()
                ?: throw IllegalArgumentException("response data is null")
        }
}
