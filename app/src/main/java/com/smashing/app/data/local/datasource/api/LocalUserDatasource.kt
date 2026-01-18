package com.smashing.app.data.local.datasource.api

interface LocalUserDatasource {
    suspend fun getUserId(): String?

    suspend fun setUserId(userId: String)

    suspend fun clearUserId()
}
