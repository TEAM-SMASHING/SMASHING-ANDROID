package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.user.GetUserInfoDetailResponse

interface UserRemoteDataSource {
    suspend fun getUserInfoDetail(): BaseResponse<GetUserInfoDetailResponse>
}
