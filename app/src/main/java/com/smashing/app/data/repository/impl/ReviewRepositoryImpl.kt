package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.review.toGameReviewList
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.review.GameReview
import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewRemoteDataSource: ReviewRemoteDataSource,
) : ReviewRepository {

    override suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?
    ): Result<CursorPage<GameReview>> =
        suspendRunCatching {
            reviewRemoteDataSource.getUserRecentReviewList(
                userId = userId,
                sportCode = sportCode,
                cursor = cursor,
                size = size,
            ).requireData().toGameReviewList()
        }
}
