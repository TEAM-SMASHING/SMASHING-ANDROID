package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.data.mapper.toRankingData
import com.smashing.app.data.mapper.user.toProfileInfo
import com.smashing.app.data.model.profile.ProfileInfo
import com.smashing.app.data.remote.datasource.api.UserRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun getUserId(): String? =
        localUserDataSource.getUserId()

    override suspend fun getUserNickname(): String? =
        localUserDataSource.getUserNickName()

    override suspend fun setUserInfo(userId: String, userNickname: String) =
        localUserDataSource.setUserInfo(userId, userNickname)

    override suspend fun clearUserInfo() =
        localUserDataSource.clearUserInfo()

    override suspend fun getUserInfoDetail(
        userId: String,
        sportCode: String?
    ): Result<ProfileInfo> =
        suspendRunCatching{
            userRemoteDataSource.getUserInfoDetail().requireData().toProfileInfo()
        }

}
