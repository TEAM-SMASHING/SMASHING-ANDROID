package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetMyRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse
import com.smashing.app.data.remote.dto.review.MyProfileReviewListData

interface ReviewRemoteDataSource {
    suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    suspend fun getMyGameReviewsResponse(cursor: String?, size: Int?): BaseResponse<MyProfileReviewListData>

    suspend fun getMyRecentReviewStats(): BaseResponse<GetMyRecentReviewStatsResponse>
}
