package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchingService {

    @GET("/api/v1/users/me/matchings/received")
    suspend fun getMeReceivedMatchingList(
        @Query("snapshotAt")
        snapshotAt: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Long?,
    ): BaseResponse<CursorDto<ReceivedMatchingListResponse>>

    @GET("/api/v1/users/me/matchings/sent")
    suspend fun getMeSentMatchingList(
        @Query("snapshotAt")
        snapshotAt: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Long?,
    ): BaseResponse<CursorDto<SentMatchingListResponse>>
}
