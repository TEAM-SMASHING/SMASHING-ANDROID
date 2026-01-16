package com.smashing.app.data.mapper

import com.smashing.app.data.model.region.KakaoRegion
import com.smashing.app.data.remote.dto.region.GetKakaoAddressSearchResponse

fun GetKakaoAddressSearchResponse.toRegionList(): List<KakaoRegion> =
    documents.map { it.toRegion() }

private fun GetKakaoAddressSearchResponse.Document.toRegion(): KakaoRegion {
    return KakaoRegion(
        addressName = addressName,
        region1depthName = roadAddress?.region1depthName
            ?: address?.region1depthName
            ?: "",
        region2depthName = roadAddress?.region2depthName
            ?: address?.region2depthName
            ?: "",
    )
}
