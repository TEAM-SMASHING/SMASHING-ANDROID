package com.smashing.app.data.local.datasource.api

interface LocalUserDatasource {
    suspend fun getUserId(): String?

    suspend fun getUserNickName(): String?

    suspend fun setUserInfo(userId: String, userNickname: String)

    suspend fun clearUserInfo()
}
