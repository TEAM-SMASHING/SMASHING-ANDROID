package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toCursorPage
import com.smashing.app.data.model.common.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import com.smashing.app.data.remote.datasource.api.RemoteMatchingDataSource
import com.smashing.app.data.remote.dto.common.requireData
import com.smashing.app.data.repository.api.MatchingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchingRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteMatchingDataSource,
) : MatchingRepository {

    override suspend fun getReceivedMatchings(
        snapshotAt: String?,
        cursor: String?,
        size: Int?,
    ): Result<CursorPage<ReceivedMatchingItem>> {
        return suspendRunCatching {
            remoteDataSource.getReceivedMatchingList(
                snapshotAt = snapshotAt,
                cursor = cursor,
                size = size,
            ).requireData()
                .toCursorPage()
        }
    }
}
