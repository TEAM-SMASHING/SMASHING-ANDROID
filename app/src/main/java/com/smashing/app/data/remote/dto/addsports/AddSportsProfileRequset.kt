package com.smashing.app.data.remote.dto.addsports

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddSportsProfileRequest(
    @SerialName("sportCode")
    val sportCode: String,

    @SerialName("experienceRange")
    val experienceRange: String
)
