package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.UserRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.user.GetUserRecentListResponse
import com.smashing.app.data.remote.service.ReviewService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService,
) : UserRemoteDataSource {

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
