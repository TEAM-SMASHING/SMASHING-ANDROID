package com.smashing.app.data.repository.api

import com.smashing.app.data.model.auth.KakaoLoginModel
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.model.auth.SignUpNickNameAvailableModel
import com.smashing.app.data.model.auth.SignUpOpenchatValidModel
import com.smashing.app.data.remote.dto.auth.PostOpenchatValidRequest
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest

interface AuthRepository {
    suspend fun postKakaoLogin(authorization: String): Result<KakaoLoginModel>
    suspend fun postSignUp(request: PostSignUpRequest): Result<SignUpModel>
    suspend fun getNicknameAvailable(nickname: String): Result<SignUpNickNameAvailableModel>
    suspend fun postOpenchatValid(request: PostOpenchatValidRequest): Result<SignUpOpenchatValidModel>
}
