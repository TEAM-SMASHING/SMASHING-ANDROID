package com.smashing.app.data.repository.api

import com.smashing.app.data.model.profile.user.UserProfileInfo
import com.smashing.app.data.model.review.GameReviewResult

interface UserRepository {
    suspend fun getUserProfileId(): String?
    suspend fun getUserNickname(): String?
    suspend fun getUserInfoDetail(
        userProfileId: String,
        sportCode: String?,
    ): Result<UserProfileInfo>
    suspend fun getUserRecentReviewStats(
        userProfileId: String,
        sportCode: String?,
    ): Result<GameReviewResult>
}
