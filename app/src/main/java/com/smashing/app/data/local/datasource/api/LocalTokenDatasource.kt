package com.smashing.app.data.local.datasource.api

interface LocalTokenDataSource {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun setTokens(accessToken: String?, refreshToken: String?)
    suspend fun clearTokens()
}
