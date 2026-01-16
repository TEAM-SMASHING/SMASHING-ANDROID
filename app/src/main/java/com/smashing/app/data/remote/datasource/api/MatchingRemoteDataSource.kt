package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse

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
}
