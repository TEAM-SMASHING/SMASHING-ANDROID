package com.smashing.app.data.remote.dto.event

import kotlinx.serialization.Serializable

@Serializable
data class MatchingReceivedDto(
    val type: String,
    val matchingId: String,
)
