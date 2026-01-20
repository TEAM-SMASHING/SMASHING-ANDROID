package com.smashing.app.data.remote.dto.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyProfileSwitchRequest(
    @SerialName("profileId")
    val profileId: String
)
