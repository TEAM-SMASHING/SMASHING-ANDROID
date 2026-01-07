package com.smashing.app.data.remote.dto.matching

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class GetMatchingListResponse(
    @SerialName("snapshotAt")
    val snapshotAt: String,
    @SerialName("matchings")
    val matchingList: List<ReceivedMatchingSummaryDto>,
    @SerialName("nextCursor")
    val nextCursor: String? = null,
    @SerialName("hasNext")
    val hasNext: Boolean,
)
