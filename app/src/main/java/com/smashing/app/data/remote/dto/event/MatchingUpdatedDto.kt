package com.smashing.app.data.remote.dto.event

import kotlinx.serialization.Serializable

@Serializable
data class MatchingUpdatedDto(
    val type: String,
    val matchingId: String,
    val status: String,
)
