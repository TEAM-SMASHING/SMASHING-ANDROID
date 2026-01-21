package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import com.smashing.app.data.remote.dto.user.GetUserRecentReviewStatsResponse

interface UserRemoteDataSource {
    suspend fun getUserInfoDetail(userId: String, sportCode: String?): BaseResponse<GetUserInfoDetailResponse>
    suspend fun getUserRecentReviewStats(userId: String, sportCode: String?): BaseResponse<GetUserRecentReviewStatsResponse>
}
