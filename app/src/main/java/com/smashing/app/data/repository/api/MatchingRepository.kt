package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching

interface MatchingRepository {
    suspend fun getMeReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): Result<CursorPage<ReceivedMatching>>

    suspend fun getMeSentMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): Result<CursorPage<SentMatching>>
}
