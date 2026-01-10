package com.smashing.app.domain.model

data class Region(
    val addressName: String,
    val cityName: String,
    val districtName: String,
) {
    val isSeoul: Boolean
        get() = cityName == "서울"

    val isDistrictBlank: Boolean
        get() = districtName.isBlank()
}