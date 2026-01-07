package com.smashing.app.data.repository.api

import com.smashing.app.data.model.common.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatchingItem

interface MatchingRepository {
    suspend fun getReceivedMatchings(
        snapshotAt: String?,
        cursor: String?,
        size: Int?,
    ): Result<CursorPage<ReceivedMatchingItem>>
}
