package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.MatchingRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.AcceptedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.remote.service.MatchingService
import com.smashing.app.data.type.OrderType
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

    override suspend fun getMeAcceptedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
        order: OrderType?
    ): BaseResponse<CursorDto<AcceptedMatchingListResponse>> =
        matchingService.getMeAcceptedMatchingList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
            order = order,
        )

    override suspend fun postAcceptedMatching(
        matchingId: String,
    ): BaseResponse<Unit> = matchingService.postAcceptedMatching(
        matchingId = matchingId,
    )

    override suspend fun deleteSentMatching(
        matchingId: String,
    ): BaseResponse<Unit> = matchingService.deleteSentMatching(
        matchingId = matchingId,
    )

    override suspend fun postRejectMatching(
        matchingId: String,
    ): BaseResponse<Unit> = matchingService.postRejectMatching(
        matchingId = matchingId,
    )

    override suspend fun postMatching(
        receiverProfileId: String
    ): BaseResponse<Unit> = matchingService.postMatching(
        receiverProfileId = receiverProfileId,
    )
}
