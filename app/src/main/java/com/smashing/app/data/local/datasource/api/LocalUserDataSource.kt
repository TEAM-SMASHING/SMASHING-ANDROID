package com.smashing.app.data.local.datasource.api

interface LocalUserDataSource {
    suspend fun getUserProfileId(): String?

    suspend fun getUserNickName(): String?

    suspend fun setUserInfo(userProfileId: String, userNickname: String)

    suspend fun clearUserInfo()
}
