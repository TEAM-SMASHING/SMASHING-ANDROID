package com.smashing.app.data.repository.api

interface UserRepository {
    suspend fun getUserId(): String?
    suspend fun getUserNickname(): String?
    suspend fun setUserInfo(userId: String, userNickname: String)
    suspend fun clearUserInfo()
}
