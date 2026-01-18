package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.ReviewRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetUserRecentListResponse
import com.smashing.app.data.remote.service.ReviewService
import javax.inject.Inject

class ReviewRemoteDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService,
) : ReviewRemoteDataSource {

    override suspend fun getUserRecentList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?
    ): BaseResponse<CursorDto<GetUserRecentListResponse>> =
        reviewService.getUserRecentList(
            userId = userId,
            sportCode = sportCode,
            cursor = cursor,
            size = size,
        )
}
