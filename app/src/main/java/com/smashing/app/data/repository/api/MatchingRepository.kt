package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.type.OrderType

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

    suspend fun getMeAcceptedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
        order: OrderType? = null,
    ): Result<CursorPage<AcceptedMatching>>

    suspend fun postAcceptedMatching(
        matchingId: String,
    ): Result<Unit>

    suspend fun deleteSentMatching(
        matchingId: String,
    ): Result<Unit>

    suspend fun postRejectMatching(
        matchingId: String,
    ): Result<Unit>

    suspend fun postMatching(
        receiverProfileId: String,
    ): Result<Unit>
}
