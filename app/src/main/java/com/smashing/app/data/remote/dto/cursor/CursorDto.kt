package com.smashing.app.data.remote.dto.cursor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CursorDto<T>(
    @SerialName("snapshotAt")
    val snapshotAt: String,
    @SerialName("results")
    val results: List<T>,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("hasNext")
    val hasNext: Boolean,
)
