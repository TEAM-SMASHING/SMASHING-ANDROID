package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.BuildConfig
import com.smashing.app.data.remote.datasource.api.KakaoRegionDataSource
import com.smashing.app.data.remote.dto.GetKakaoAddressSearchResponse
import com.smashing.app.data.remote.service.KakaoService
import javax.inject.Inject

class KakaoRegionDataSourceImpl @Inject constructor(
    private val kakaoLocalService: KakaoService,
) : KakaoRegionDataSource {
    override suspend fun searchAddress(query: String): Result<GetKakaoAddressSearchResponse> {
        return runCatching {
            val authHeader = "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}"
            kakaoLocalService.searchAddress(
                authorization = authHeader,
                query = query,
            )
        }
    }
}