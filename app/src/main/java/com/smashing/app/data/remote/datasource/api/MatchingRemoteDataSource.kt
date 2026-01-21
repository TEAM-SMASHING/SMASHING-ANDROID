package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.AcceptedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.type.OrderType
import retrofit2.http.Path

interface MatchingRemoteDataSource {
    suspend fun getMeReceivedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): BaseResponse<CursorDto<ReceivedMatchingListResponse>>

    suspend fun getMeSentMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): BaseResponse<CursorDto<SentMatchingListResponse>>

    suspend fun getMeAcceptedMatchingList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
        order: OrderType?,
    ): BaseResponse<CursorDto<AcceptedMatchingListResponse>>

    suspend fun postAcceptedMatching(
        matchingId: String,
    ): BaseResponse<Unit>

    suspend fun deleteSentMatching(
        matchingId: String,
    ): BaseResponse<Unit>

    suspend fun postRejectMatching(
        matchingId: String,
    ): BaseResponse<Unit>

    suspend fun putCancelGame(
        gameId: String,
    ): BaseResponse<Unit>

    suspend fun postMatching(
        receiverProfileId: String,
    ): BaseResponse<Unit>
}
