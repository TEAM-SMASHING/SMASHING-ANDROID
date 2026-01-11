package com.smashing.app.data.remote.datasource.api

import android.content.Context

interface KakaoAuthDataSource {
    suspend fun loginKakao(context: Context): Result<String>
}
