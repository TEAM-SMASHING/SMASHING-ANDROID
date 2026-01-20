package com.smashing.app.data.repository.impl

import com.smashing.app.data.local.datasource.api.LocalUserDatasource
import com.smashing.app.data.repository.api.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDatasource: LocalUserDatasource,
) : UserRepository {
    override suspend fun getUserId(): String? =
        localUserDatasource.getUserId()

    override suspend fun getUserNickname(): String? =
        localUserDatasource.getUserNickName()

    override suspend fun setUserInfo(userId: String, userNickname: String) =
        localUserDatasource.setUserInfo(userId, userNickname)

    override suspend fun clearUserInfo() =
        localUserDatasource.clearUserInfo()
}
