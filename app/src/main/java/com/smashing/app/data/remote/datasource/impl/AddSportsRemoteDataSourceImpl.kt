package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.AddSportsRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.addsports.AddSportsProfileRequest
import com.smashing.app.data.remote.service.AddSportsService
import jakarta.inject.Inject

class AddSportsRemoteDataSourceImpl @Inject constructor(
    private val addSportsService: AddSportsService
) : AddSportsRemoteDataSource {
    override suspend fun addSportsProfile(request: AddSportsProfileRequest): BaseResponse<String?> {
        return addSportsService.addSportProfile(request)
    }
}
