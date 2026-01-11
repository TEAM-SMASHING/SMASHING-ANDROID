package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.BuildConfig
import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.remote.dto.region.kakaoRegion.GetKakaoAddressSearchResponse
import com.smashing.app.data.remote.service.KakaoRegionService
import javax.inject.Inject

class KakaoRegionDataSourceImpl @Inject constructor(
    private val kakaoLocalService: KakaoRegionService,
) : KakaoRegionDataSource {
    override suspend fun searchAddress(query: String): GetKakaoAddressSearchResponse {
        val authHeader = "KakaoAK ${BuildConfig.KAKAO_API_KEY}"
        return kakaoLocalService.searchAddress(
            authorization = authHeader,
            query = query,
        )
    }
}