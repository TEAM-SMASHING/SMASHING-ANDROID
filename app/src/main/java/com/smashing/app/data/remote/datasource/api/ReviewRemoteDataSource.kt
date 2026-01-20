package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.MyProfileReviewListData
import com.smashing.app.data.remote.dto.review.GetUserRecentReviewListResponse

interface ReviewRemoteDataSource {
    suspend fun getUserRecentReviewList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<CursorDto<GetUserRecentReviewListResponse>>

    suspend fun getMyGameReviews(cursor: String?, size: Int?): BaseResponse<MyProfileReviewListData>

}
