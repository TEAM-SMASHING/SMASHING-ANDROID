package com.smashing.app.data.mapper

import com.smashing.app.data.model.Region
import com.smashing.app.data.remote.dto.region.kakaoRegion.GetKakaoAddressSearchResponse

fun GetKakaoAddressSearchResponse.toRegionList(): List<Region> =
    documents.map { it.toRegion() }

private fun GetKakaoAddressSearchResponse.Document.toRegion(): Region {
    return Region(
        addressName = addressName,
        cityName = roadAddress?.region1depthName
            ?: address?.region1depthName
            ?: "",
        districtName = roadAddress?.region2depthName
            ?: address?.region2depthName
            ?: "",
    )
}