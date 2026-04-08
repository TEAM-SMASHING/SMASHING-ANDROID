package com.smashing.app.data.repository.api

import com.smashing.app.data.model.review.GameReviewResult
import com.smashing.app.data.model.profile.user.UserProfileInfo

interface UserRepository {
    suspend fun getUserProfileId(): String?
    suspend fun getUserNickname(): String?
    suspend fun setUserInfo(userProfileId: String, userNickname: String)
    suspend fun clearUserInfo()
    suspend fun getUserInfoDetail(
        userProfileId: String,
        sportCode: String?,
    ): Result<UserProfileInfo>
    suspend fun getUserRecentReviewStats(
        userProfileId: String,
        sportCode: String?,
    ): Result<GameReviewResult>
}
