package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.MyProfileReviewListData
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse
import com.smashing.app.data.remote.service.ReviewService
import javax.inject.Inject

class ReviewRemoteDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService,
) : ReviewRemoteDataSource {

    override suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>> =
        reviewService.getUserRecentReviewList(
            userId = userId,
            sportCode = sportCode,
            cursor = cursor,
            size = size,
        )

    override suspend fun getMyGameReviews(
        cursor: String?,
        size: Int?
    ): BaseResponse<MyProfileReviewListData> {
        return reviewService.getMyGameReviews(cursor, size)
    }
}
