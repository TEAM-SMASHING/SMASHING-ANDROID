package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toRegionList
import com.smashing.app.data.model.region.KakaoRegion
import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.remote.datasource.api.RegionRemoteDataSource
import com.smashing.app.data.repository.api.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionDataSource: KakaoRegionDataSource,
    private val regionRemoteDataSource: RegionRemoteDataSource,
) : RegionRepository {

    override suspend fun searchAddress(query: String): Result<List<KakaoRegion>> {
        return suspendRunCatching {
            val response = regionDataSource.searchAddress(query)

           response.toRegionList()
        }
    }

    override suspend fun changeRegion(region: String): Result<Unit> = suspendRunCatching {
        val response = regionRemoteDataSource.putRegionChange(region)

        if (response.statusCode != 200) {
            throw IllegalStateException("Region change failed: ${response.status}")
        }
    }
}
