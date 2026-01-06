package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.region.kakaoRegion.GetKakaoAddressSearchResponse

interface KakaoRegionDataSource {
    suspend fun searchAddress(query: String): GetKakaoAddressSearchResponse
}