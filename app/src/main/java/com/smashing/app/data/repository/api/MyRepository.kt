package com.smashing.app.data.repository.api

import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.model.profile.my.MyProfileInfo
import com.smashing.app.data.model.profile.home.MyProfileTierInfo
import com.smashing.app.data.model.review.GameReviewResult


interface MyRepository {
    suspend fun getMyTierProfile(): Result<MyProfileTierInfo>
    suspend fun getMyProfileInfo(): Result<MyProfileInfo>

    suspend fun switchActiveMyProfile(profileId: String): Result<Unit>

    suspend fun addSportsProfile(info: AddSportsInfo): Result<Unit>

    suspend fun getMyRecentReviewStats(): Result<GameReviewResult>
    suspend fun postLogout(): Result<Unit>
}
