package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.region.RegionChangeRequest
import retrofit2.http.Body
import retrofit2.http.PUT

interface RegionService {

    @PUT("/api/v1/users/me/regions")
    suspend fun putRegionChange(
        @Body request: RegionChangeRequest,
    ): BaseResponse<Unit?>
}