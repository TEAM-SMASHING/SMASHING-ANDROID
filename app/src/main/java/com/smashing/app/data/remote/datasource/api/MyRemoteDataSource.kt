package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.my.GetMyTierProfileResponse

interface MyRemoteDataSource {
    suspend fun getMyTierProfile(): BaseResponse<GetMyTierProfileResponse>
}