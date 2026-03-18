package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.local.datasource.api.LocalTokenDataSource
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.data.mapper.auth.toKakaoLoginToken
import com.smashing.app.data.mapper.auth.toSignUpModel
import com.smashing.app.data.mapper.auth.toSignUpNickNameAvailableModel
import com.smashing.app.data.mapper.auth.toSignUpOpenchatValidModel
import com.smashing.app.data.model.auth.KakaoLoginModel
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.model.auth.SignUpNickNameAvailableModel
import com.smashing.app.data.model.auth.SignUpOpenchatValidModel
import com.smashing.app.data.remote.datasource.api.AuthRemoteDataSource
import com.smashing.app.data.remote.dto.auth.PostKakaoLoginRequest
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val tokenDataStore: LocalTokenDataSource,
    private val userDataStore: LocalUserDataSource,
) : AuthRepository {

    override suspend fun postKakaoLogin(authorization: String): Result<KakaoLoginModel> =
        suspendRunCatching {
            val response = authRemoteDataSource.postKakaoLogin(PostKakaoLoginRequest(authorization)).requireData()

            val loginModel = response.toKakaoLoginToken()
            val (accessToken, refreshToken) = loginModel.accessToken to loginModel.refreshToken
            val (userId, userNickname) = loginModel.userId to loginModel.userNickname

            if(!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()
                && !userId.isNullOrEmpty() && !userNickname.isNullOrEmpty()) {
                    tokenDataStore.setTokens(
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                    )
                    userDataStore.setUserInfo(
                        userId = userId,
                        userNickname = userNickname,
                    )
            }

            loginModel
        }

    override suspend fun postSignUp(request: PostSignUpRequest): Result<SignUpModel> =
        suspendRunCatching {
            val response = authRemoteDataSource.postSignUp(request).requireData()

            val signUpModel = response.toSignUpModel()

            tokenDataStore.setTokens(
                accessToken = signUpModel.accessToken,
                refreshToken = signUpModel.refreshToken,
            )

            userDataStore.setUserInfo(
                userId = signUpModel.userId,
                userNickname = signUpModel.userNickname,
            )

            signUpModel
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

    override suspend fun postLogout(token: String): Result<Unit> =
        suspendRunCatching {
            authRemoteDataSource.postLogout(token).requireData()
        }

}
