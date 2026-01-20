package com.smashing.app.data.repository.api

import com.smashing.app.data.model.addsports.AddSportsInfo
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.review.GameReview


interface MyRepository {
    suspend fun getMyPageInfo(): Result<MyPageInfo>

    suspend fun getMyGameReviews(
        cursor: String?,
        size: Int?,
    ): Result<CursorPage<GameReview>>

    suspend fun switchActiveMyProfile(profileId: String): Result<Unit>

    suspend fun addSportsProfile(info: AddSportsInfo): Result<Unit>

    suspend fun getMyTierProfile(): Result<UserProfile>
}