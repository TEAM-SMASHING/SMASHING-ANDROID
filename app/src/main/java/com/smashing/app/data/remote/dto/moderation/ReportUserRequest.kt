package com.smashing.app.data.remote.dto.moderation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportUserRequest(
    @SerialName("reportedUserProfileId")
    val reportedUserProfileId: String,
    @SerialName("reportType")
    val reportType: String,
    @SerialName("reasonDetail")
    val reasonDetail: String? = null,
)
