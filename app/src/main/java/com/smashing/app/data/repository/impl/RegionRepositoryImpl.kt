package com.smashing.app.data.repository.impl

import com.smashing.app.data.model.Region
import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.repository.api.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionDataSource: KakaoRegionDataSource,
) : RegionRepository {
    override suspend fun searchAddress(query: String): Result<List<Region>> {
        return regionDataSource.searchAddress(query).mapCatching { response ->
            response.documents.map { document ->
                val address = document.address
                val roadAddress = document.roadAddress

                Region(
                    addressName = document.addressName,
                    region2depthName = address?.region2depthName
                        ?: roadAddress?.region2depthName
                        ?: "",
                )
            }
        }
    }
}