package com.smashing.app.data.repository.api

import android.content.Context
import com.smashing.app.data.model.auth.KakaoLoginModel
import com.smashing.app.data.model.auth.SignUpModel
import com.smashing.app.data.model.auth.SignUpNickNameAvailableModel
import com.smashing.app.data.remote.dto.auth.PostSignUpRequest

interface AuthRepository {
    suspend fun loginKakao(context: Context): Result<String>
    suspend fun postKakaoLogin(authorization: String): Result<KakaoLoginModel>
    suspend fun postSignUp(request: PostSignUpRequest): Result<SignUpModel>
    suspend fun getNicknameAvailable(nickname: String): Result<SignUpNickNameAvailableModel>
}
