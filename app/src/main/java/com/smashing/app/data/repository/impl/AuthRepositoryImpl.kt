package com.smashing.app.data.repository.impl

import android.content.Context
import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.mapper.toKakaoLoginToken
import com.smashing.app.data.mapper.toSignUpModel
import com.smashing.app.data.mapper.toSignUpNickNameAvailableModel
import com.smashing.app.data.mapper.toSignUpOpenchatValidModel
import com.smashing.app.data.model.auth.KakaoLoginModel
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.model.auth.SignUpNickNameAvailableModel
import com.smashing.app.data.model.auth.SignUpOpenchatValidModel
import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.datasource.api.KakaoAuthDataSource
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val kakaoAuthDataSource: KakaoAuthDataSource,
    private val tokenDataStore: LocalTokenDataSource,
) : AuthRepository {

    override suspend fun loginKakao(context: Context): Result<String> =
        kakaoAuthDataSource.loginKakao(context)

    override suspend fun postKakaoLogin(authorization: String): Result<KakaoLoginModel> =
        suspendRunCatching {
            val response = authRemoteDataSource.postKakaoLogin(PostKakaoLoginRequest(authorization)).requireData()

            val loginModel = response.toKakaoLoginToken()

            when(!loginModel.accessToken.isNullOrEmpty() && !loginModel.refreshToken.isNullOrEmpty()) {
                true -> {
                    tokenDataStore.setTokens(
                        accessToken = loginModel.accessToken,
                        refreshToken = loginModel.refreshToken,
                    )
                }
                false -> {}
            }
            loginModel
        }

    override suspend fun postSignUp(request: PostSignUpRequest): Result<SignUpModel> =
        suspendRunCatching {
            val response = authRemoteDataSource.postSignUp(request).requireData()

            response.toSignUpModel()
        }

    override suspend fun getNicknameAvailable(nickname: String): Result<SignUpNickNameAvailableModel> =
        suspendRunCatching {
            val response = authRemoteDataSource.getNicknameAvailable(nickname).requireData()

            response.toSignUpNickNameAvailableModel()
        }

    override suspend fun postOpenchatValid(request: PostOpenchatValidRequest): Result<SignUpOpenchatValidModel> =
        suspendRunCatching {
            val response = authRemoteDataSource.postOpenchatValid(request).requireData()

            response.toSignUpOpenchatValidModel()
        }
    
}
