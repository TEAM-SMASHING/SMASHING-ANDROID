package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse

interface SearchRemoteDataSource {
    suspend fun getRegionUsersSearch(
        cursor: String?,
        size: Int?,
        gender: String?,
        tier: String?,
    ): BaseResponse<CursorDto<GetRegionUsersSearchResponse>>
}
