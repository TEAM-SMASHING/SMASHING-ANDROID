package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toAcceptedMatchingList
import com.smashing.app.data.mapper.toReceivedMatchingList
import com.smashing.app.data.mapper.toSentMatchingList
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.remote.datasource.api.MatchingRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.MatchingRepository
import com.smashing.app.data.type.OrderType
import javax.inject.Inject

class MatchingRepositoryImpl @Inject constructor(
    private val matchingRemoteDataSource: MatchingRemoteDataSource,
) : MatchingRepository {
    override suspend fun getMeReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?
    ): Result<CursorPage<ReceivedMatching>> = suspendRunCatching {
        matchingRemoteDataSource.getMeReceivedMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size
        ).requireData().toReceivedMatchingList()
    }

    override suspend fun getMeSentMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?
    ): Result<CursorPage<SentMatching>> = suspendRunCatching {
        matchingRemoteDataSource.getMeSentMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
        ).requireData().toSentMatchingList()
    }

    override suspend fun getMeAcceptedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
        order: OrderType?
    ): Result<CursorPage<AcceptedMatching>> = suspendRunCatching {
        matchingRemoteDataSource.getMeAcceptedMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
            order = order,
        ).requireData().toAcceptedMatchingList()
    }

}
