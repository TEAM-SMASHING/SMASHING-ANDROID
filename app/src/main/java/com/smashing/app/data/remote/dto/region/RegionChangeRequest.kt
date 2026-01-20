package com.smashing.app.data.remote.dto.region

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionChangeRequest(
    @SerialName("region")
    val region: String,
)