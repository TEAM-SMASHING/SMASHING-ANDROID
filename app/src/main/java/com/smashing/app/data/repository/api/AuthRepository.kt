package com.smashing.app.data.repository.api

import android.content.Context
import com.smashing.app.data.model.auth.AuthModel
import com.smashing.app.data.remote.dto.PostSignUpRequest

interface AuthRepository {
    suspend fun loginKakao(context: Context): Result<String>
    suspend fun postKakaoLogin(authorization: String): Result<AuthModel>
    suspend fun postSignUp(request: PostSignUpRequest): Result<AuthModel>
}
