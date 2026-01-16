package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toDataModel
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.remote.datasource.api.MatchingRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.MatchingRepository
import javax.inject.Inject

class MatchingRepositoryImpl @Inject constructor(
    private val matchingRemoteDataSource: MatchingRemoteDataSource,
) : MatchingRepository {
    override suspend fun getMeReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?
    ): Result<CursorPage<ReceivedMatching>> = suspendRunCatching {
        matchingRemoteDataSource.getMeReceivedMatchingList(snapshotAt, cursor, size).requireData()
            .toDataModel()
    }

    override suspend fun getMeSentMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?
    ): Result<CursorPage<SentMatching>> = suspendRunCatching {
        matchingRemoteDataSource.getMeSentMatchingList(snapshotAt, cursor, size).requireData()
            .toDataModel()
    }

}
