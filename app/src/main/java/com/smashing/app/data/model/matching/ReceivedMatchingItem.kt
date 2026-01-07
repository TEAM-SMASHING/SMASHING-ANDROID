package com.smashing.app.data.model.matching

import java.time.OffsetDateTime

data class ReceivedMatchingItem(
    val matchingId: String,
    val createdAt: OffsetDateTime,
    val status: String,
    val requesterSummary: RequesterSummary,
)
