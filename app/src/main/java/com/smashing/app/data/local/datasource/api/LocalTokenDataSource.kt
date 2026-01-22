package com.smashing.app.data.local.datasource.api

import kotlinx.coroutines.flow.Flow

interface LocalTokenDataSource {
    val accessTokenFlow: Flow<String?>
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun setTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}
