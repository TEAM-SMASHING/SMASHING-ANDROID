package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.SearchRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import com.smashing.app.data.remote.service.SearchService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchRemoteDataSource {
    override suspend fun getRegionUsersSearch(
        cursor: String?,
        size: Int?,
        gender: String?,
        tier: String?
    ): BaseResponse<CursorDto<GetRegionUsersSearchResponse>> =
        searchService.getRegionUsersSearch(
            cursor = cursor,
            size = size,
            gender = gender,
            tier = tier,
        )

}
