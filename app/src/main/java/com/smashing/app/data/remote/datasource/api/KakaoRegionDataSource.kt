package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.GetKakaoAddressSearchResponse

interface KakaoRegionDataSource {
    suspend fun searchAddress(query: String): Result<GetKakaoAddressSearchResponse>
}