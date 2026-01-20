package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse

interface RegionRemoteDataSource {
    suspend fun putRegionChange(region: String): BaseResponse<Unit?>
}