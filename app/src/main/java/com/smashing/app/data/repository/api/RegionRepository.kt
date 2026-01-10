package com.smashing.app.data.repository.api

import com.smashing.app.data.model.KakaoRegion

interface RegionRepository {
    suspend fun searchAddress(query: String): Result<List<KakaoRegion>>
}