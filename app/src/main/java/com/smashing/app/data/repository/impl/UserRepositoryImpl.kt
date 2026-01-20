package com.smashing.app.data.repository.impl

import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.data.repository.api.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {
    override suspend fun getUserId(): String? =
        localUserDataSource.getUserId()

    override suspend fun getUserNickname(): String? =
        localUserDataSource.getUserNickName()

    override suspend fun setUserInfo(userId: String, userNickname: String) =
        localUserDataSource.setUserInfo(userId, userNickname)

    override suspend fun clearUserInfo() =
        localUserDataSource.clearUserInfo()
}
