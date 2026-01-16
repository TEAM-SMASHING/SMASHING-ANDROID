package com.smashing.app.presentation.region.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.smashing.app.domain.model.Region

private object RegionResultKey {
    const val ADDRESS_NAME = "region_address_name"
    const val CITY_NAME = "region_city_name"
    const val DISTRICT_NAME = "region_district_name"
}

fun NavController.setRegionResult(region: Region) {
    previousBackStackEntry?.savedStateHandle?.apply {
        set(RegionResultKey.ADDRESS_NAME, region.addressName)
        set(RegionResultKey.CITY_NAME, region.cityName)
        set(RegionResultKey.DISTRICT_NAME, region.districtName)
    }
}

fun SavedStateHandle.getRegionResult(): Region? {
    val addressName = get<String>(RegionResultKey.ADDRESS_NAME)
    val cityName = get<String>(RegionResultKey.CITY_NAME)
    val districtName = get<String>(RegionResultKey.DISTRICT_NAME)

    return if (addressName != null && cityName != null && districtName != null) {
        Region(addressName, cityName, districtName)
    } else null
}

fun SavedStateHandle.removeRegionResult() {
    remove<String>(RegionResultKey.ADDRESS_NAME)
    remove<String>(RegionResultKey.CITY_NAME)
    remove<String>(RegionResultKey.DISTRICT_NAME)
}
