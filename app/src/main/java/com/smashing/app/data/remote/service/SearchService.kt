package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/api/v1/users/me/regions/users?")
    suspend fun getRegionUsersSearch(
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Int?,
        @Query("gender")
        gender: String?,
        @Query("tier")
        tier: String?,
    ): BaseResponse<CursorDto<GetRegionUsersSearchResponse>>

}
