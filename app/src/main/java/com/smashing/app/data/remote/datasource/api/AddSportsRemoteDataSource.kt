package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.addsports.AddSportProfileRequest

interface AddSportsRemoteDataSource {
    suspend fun addSportProfile(request: AddSportProfileRequest): BaseResponse<String?>
}
