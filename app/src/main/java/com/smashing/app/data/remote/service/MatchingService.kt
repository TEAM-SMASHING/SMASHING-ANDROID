package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.common.BaseResponse
import com.smashing.app.data.remote.dto.matching.GetMatchingListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchingService {
    @GET("/api/v1/users/me/matchings/received")
    suspend fun getReceivedMatchings(
        @Query("snapshotAt") snapshotAt: String?,
        @Query("cursor") cursor: String?,
        @Query("size") size: Int?,
    ): BaseResponse<GetMatchingListResponse>
}
