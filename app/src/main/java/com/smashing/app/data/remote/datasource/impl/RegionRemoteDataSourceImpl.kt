package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.RegionRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.region.RegionChangeRequest
import com.smashing.app.data.remote.service.RegionService
import javax.inject.Inject

class RegionRemoteDataSourceImpl @Inject constructor(
    private val regionService: RegionService,
) : RegionRemoteDataSource {

    override suspend fun putRegionChange(region: String): BaseResponse<Unit?> =
        regionService.putRegionChange(
            request = RegionChangeRequest(region = region)
        )
}