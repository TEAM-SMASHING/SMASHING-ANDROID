package com.smashing.app.data.repository.api

import com.smashing.app.data.model.Region

interface KakaoRegionRepository {
    suspend fun searchAddress(query: String): Result<List<Region>>
}