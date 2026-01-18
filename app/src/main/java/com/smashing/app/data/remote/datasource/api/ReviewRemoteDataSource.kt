package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.review.GetUserRecentListResponse

interface ReviewRemoteDataSource {
    suspend fun getUserRecentList(
        userId: String,
        sportCode: String?,
        cursor: String?,
        size: Int?,
    ): BaseResponse<CursorDto<GetUserRecentListResponse>>
}
