package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetReviewDetailResponse
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewListResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse
import com.smashing.app.data.remote.service.ReviewService
import javax.inject.Inject

class ReviewRemoteDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService,
) : ReviewRemoteDataSource {

    override suspend fun getReview(
        reviewId: String,
    ): BaseResponse<GetReviewDetailResponse> =
        reviewService.getReview(reviewId = reviewId)

    override suspend fun getUserRecentReviewList(
        userProfileId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>> =
        reviewService.getUserRecentReviewList(
            userProfileId = userProfileId,
            sportCode = sportCode,
            cursor = cursor,
            size = size,
            snapshotAt = snapshotAt,
        )

    override suspend fun getMyRecentReviewList(
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): BaseResponse<CursorDto<GetMyRecentReviewListResponse>> =
        reviewService.getMyRecentReviewList(
            cursor = cursor,
            size = size,
            snapshotAt = snapshotAt
        )

}
