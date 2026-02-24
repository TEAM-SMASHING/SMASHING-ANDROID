package com.smashing.app.data.repository.api

import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.model.profile.user.UserProfileInfo

interface UserRepository {
    suspend fun getUserId(): String?
    suspend fun getUserNickname(): String?
    suspend fun setUserInfo(userId: String, userNickname: String)
    suspend fun clearUserInfo()
    suspend fun getUserInfoDetail(
        userId: String,
        sportCode: String?,
    ): Result<UserProfileInfo>
    suspend fun getUserRecentReviewStats(
        userId: String,
        sportCode: String?,
    ): Result<GameReviewResult>
}
