package com.smashing.app.data.remote.dto.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddSportProfileRequest(
    @SerialName("sportCode")
    val sportCode: String,

    @SerialName("experienceRange")
    val experienceRange: String
)
