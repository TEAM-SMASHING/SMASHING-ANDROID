package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.local.datasource.api.LocalUserDataSource
import com.smashing.app.data.mapper.user.toGameReviewResult
import com.smashing.app.data.mapper.user.toUserProfileInfo
import com.smashing.app.data.model.profile.user.UserProfileInfo
import com.smashing.app.data.model.review.GameReviewResult
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
    ): Result<UserProfileInfo> =
        suspendRunCatching {
            userRemoteDataSource.getUserInfoDetail(userId, sportCode).requireData()
                .toUserProfileInfo()
        }.recoverCatching { exception ->
            if (exception is retrofit2.HttpException) {
                val errorBody = exception.response()?.errorBody()?.string()
                when {
                    errorBody?.contains("USER-003") == true -> {
                        throw Exception("존재하지 않는 유저입니다.")
                    }

                    errorBody?.contains("USER-002") == true -> {
                        throw Exception("활성화된 스포츠 프로필이 없습니다.")
                    }

                    else -> throw exception
                }
            }
            throw exception
        }

    override suspend fun getUserRecentReviewStats(
        userId: String,
        sportCode: String?
    ): Result<GameReviewResult> =
        suspendRunCatching {
            userRemoteDataSource.getUserRecentReviewStats(userId, sportCode).requireData()
                .toGameReviewResult()
        }

}
