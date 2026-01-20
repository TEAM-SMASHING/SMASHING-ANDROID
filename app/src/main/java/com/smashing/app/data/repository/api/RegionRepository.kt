package com.smashing.app.data.repository.api

import com.smashing.app.data.model.region.KakaoRegion

interface RegionRepository {
    suspend fun searchAddress(query: String): Result<List<KakaoRegion>>

    suspend fun changeRegion(region: String): Result<Unit>
}
