package com.smashing.app.data.remote.dto.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockUserRequest(
    @SerialName("blockedUserProfileId")
    val blockedUserProfileId: String,
)
