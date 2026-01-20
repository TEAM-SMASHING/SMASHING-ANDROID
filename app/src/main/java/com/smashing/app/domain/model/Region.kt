package com.smashing.app.domain.model

data class Region(
    val addressName: String,
    val cityName: String,
    val districtName: String,
){
    val isSeoulDistrict: Boolean
        get() = cityName == "서울" && districtName.isNotBlank() && districtName.endsWith("구")
}