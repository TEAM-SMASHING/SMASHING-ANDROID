package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetReviewDetailResponse
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewListResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse

interface ReviewRemoteDataSource {

    suspend fun getReview(
        reviewId: String,
    ): BaseResponse<GetReviewDetailResponse>

    suspend fun getUserRecentReviewList(
        userProfileId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
        snapshotAt: String?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    suspend fun getMyRecentReviewList(
        cursor: String?,
        size: Int?,
        snapshotAt: String?
    ): BaseResponse<CursorDto<GetMyRecentReviewListResponse>>
}
