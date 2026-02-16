package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.UserRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.user.GetUserRecentReviewStatsResponse
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse
import com.smashing.app.data.remote.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserRemoteDataSource {

    override suspend fun getUserInfoDetail(
        userId: String,
        sportCode: String?
    ): BaseResponse<GetUserInfoDetailResponse> =
        userService.getUserInfoDetail(userId, sportCode)

    override suspend fun getUserRecentReviewStats(
        userId: String,
        sportCode: String?
    ): BaseResponse<GetUserRecentReviewStatsResponse> =
        userService.getUserRecentReviewStats(userId, sportCode)

}
