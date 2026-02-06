package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.my.UserProfile
import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.my.AddSportsInfo
import com.smashing.app.data.model.review.GameReview


interface MyRepository {
    suspend fun getMyPageInfo(): Result<MyPageInfo>

    suspend fun switchActiveMyProfile(profileId: String): Result<Unit>

    suspend fun addSportsProfile(info: AddSportsInfo): Result<Unit>

    suspend fun getMyTierProfile(): Result<UserProfile>
}
