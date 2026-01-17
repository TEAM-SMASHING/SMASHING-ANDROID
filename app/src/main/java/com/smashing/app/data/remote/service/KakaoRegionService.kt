package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.region.GetKakaoAddressSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoRegionService {

    @GET("/v2/local/search/address.json")
    suspend fun searchAddress(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
    ): GetKakaoAddressSearchResponse
}
