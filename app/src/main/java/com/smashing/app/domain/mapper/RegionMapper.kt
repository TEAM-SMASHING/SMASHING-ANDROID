package com.smashing.app.domain.mapper

import com.smashing.app.data.model.KakaoRegion
import com.smashing.app.domain.model.Region

fun KakaoRegion.toRegion(): Region {
    return Region(
        addressName = addressName,
        cityName = region1depthName,
        districtName = region2depthName,
    )
}