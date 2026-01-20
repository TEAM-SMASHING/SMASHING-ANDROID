package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toGameReviewPage
import com.smashing.app.data.mapper.toMyPageInfo
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.profile.MyPageInfo
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.remote.datasource.api.MyRemoteDataSource
import com.smashing.app.data.remote.dto.profile.my.MyProfileSwitchRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.remote.service.MyService
import com.smashing.app.data.repository.api.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource,
    private val myService: MyService
) : MyRepository {

    override suspend fun getMyPageInfo(): Result<MyPageInfo> = suspendRunCatching {
        myRemoteDataSource.getMyProfile()
            .requireData()
            .toMyPageInfo()
    }

    override suspend fun getMyGameReviews(
        cursor: String?,
        size: Int?
    ): Result<CursorPage<GameReview>> = suspendRunCatching {
        myRemoteDataSource.getMyGameReviews(cursor, size)
            .requireData()
            .toGameReviewPage()
    }

    override suspend fun switchActiveMyProfile(profileId: String): Result<Unit> {
        return runCatching {
            myService
                .switchActiveMyProfile(MyProfileSwitchRequest(profileId = profileId))
                .requireData()
        }
    }
}
