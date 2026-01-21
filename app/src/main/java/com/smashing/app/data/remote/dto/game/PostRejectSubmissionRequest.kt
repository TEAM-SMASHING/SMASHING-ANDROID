package com.smashing.app.data.remote.dto.game

import com.smashing.app.data.type.ConfirmDenyType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRejectSubmissionRequest(
    @SerialName("reason")
    val reason: ConfirmDenyType?,
)
