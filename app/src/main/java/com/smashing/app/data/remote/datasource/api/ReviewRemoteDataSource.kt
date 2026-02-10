package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetReviewDetailResponse
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewListResponse
import com.smashing.app.data.remote.dto.my.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse

interface ReviewRemoteDataSource {

    suspend fun getReview(
        reviewId: String,
    ): BaseResponse<GetReviewDetailResponse>

    suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    suspend fun getMyRecentReviewList(
        cursor: String?,
        size: Int?
    ): BaseResponse<CursorDto<GetMyRecentReviewListResponse>>

    suspend fun getMyRecentReviewStats(): BaseResponse<GetMyRecentReviewStatsResponse>
}
