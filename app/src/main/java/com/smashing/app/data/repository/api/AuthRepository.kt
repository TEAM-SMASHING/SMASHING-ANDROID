package com.smashing.app.data.repository.api

import android.content.Context
import com.smashing.app.data.model.KakaoLoginToken

interface AuthRepository {
    suspend fun loginKakao(context: Context): Result<String>
    suspend fun fetchKakaoLogin(authorization: String): Result<KakaoLoginToken>
}
