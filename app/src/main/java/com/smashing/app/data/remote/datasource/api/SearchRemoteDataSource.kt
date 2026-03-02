package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.search.GetNicknameUsersSearchResponse
import com.smashing.app.data.remote.dto.search.GetRecommendedUsersResponse
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import com.smashing.app.data.remote.dto.search.GetUserRegionResponse

interface SearchRemoteDataSource {
    suspend fun getRegionUsersSearch(
        cursor: String?,
        size: Int?,
        gender: String?,
        tier: String?,
        snapshotAt: String?,
    ): BaseResponse<CursorDto<GetRegionUsersSearchResponse>>

    suspend fun getNickNameUsersSearch(
        nickname: String,
    ): BaseResponse<GetNicknameUsersSearchResponse>

    suspend fun getRecommendedUsers(): BaseResponse<GetRecommendedUsersResponse>

    suspend fun getUserRegion(): BaseResponse<GetUserRegionResponse>

}
