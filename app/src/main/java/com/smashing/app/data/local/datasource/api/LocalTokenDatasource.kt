package com.smashing.app.data.local.datasource.api

interface LocalTokenDataSource {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)
    suspend fun clearTokens()
}
