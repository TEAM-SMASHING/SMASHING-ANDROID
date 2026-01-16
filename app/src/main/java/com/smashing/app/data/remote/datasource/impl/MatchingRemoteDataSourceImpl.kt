package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.MatchingRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.remote.service.MatchingService
import javax.inject.Inject

class MatchingRemoteDataSourceImpl @Inject constructor(
    private val matchingService: MatchingService,
) : MatchingRemoteDataSource {

    override suspend fun getMeReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?
    ): BaseResponse<CursorDto<ReceivedMatchingListResponse>> =
        matchingService.getMeReceivedMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
        )

    override suspend fun getMeSentMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?
    ): BaseResponse<CursorDto<SentMatchingListResponse>> =
        matchingService.getMeSentMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
        )
}
