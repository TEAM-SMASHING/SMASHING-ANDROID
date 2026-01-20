package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.addsports.AddSportsProfileRequest

interface AddSportsRemoteDataSource {
    suspend fun addSportsProfile(request: AddSportsProfileRequest): BaseResponse<String?>
}
