package com.smashing.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Region(
    val addressName: String,
    val cityName: String,
    val districtName: String,
) : Parcelable {
    val isSeoulDistrict: Boolean
        get() = cityName == "서울" && districtName.isNotBlank()
}