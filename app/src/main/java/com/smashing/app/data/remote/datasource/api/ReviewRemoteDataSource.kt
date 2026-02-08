package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetReviewResponse
import com.smashing.app.data.remote.dto.my.GetMyGameReviewsResponse
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse

interface ReviewRemoteDataSource {

    suspend fun getReview(
        reviewId: String,
    ): BaseResponse<GetReviewResponse>

    suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    suspend fun getMyGameReviews(cursor: String?, size: Int?): BaseResponse<GetMyGameReviewsResponse>

    suspend fun getMyRecentReviewStats(): BaseResponse<GetMyRecentReviewStatsResponse>
}
