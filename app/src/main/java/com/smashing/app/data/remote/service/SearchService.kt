package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.search.GetNicknameUsersSearchResponse
import com.smashing.app.data.remote.dto.search.GetRecommendedUsersResponse
import com.smashing.app.data.remote.dto.search.GetRegionUsersSearchResponse
import com.smashing.app.data.remote.dto.search.GetUserRegionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/api/v1/users/me/regions/users")
    suspend fun getRegionUsersSearch(
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Int?,
        @Query("gender")
        gender: String?,
        @Query("tier")
        tier: String?,
        @Query("snapshotAt")
        snapshotAt: String?,
    ): BaseResponse<CursorDto<GetRegionUsersSearchResponse>>

    @GET("/api/v1/users/search")
    suspend fun getNickNameUsersSearch(
        @Query("nickname")
        nickname: String,
    ): BaseResponse<GetNicknameUsersSearchResponse>

    @GET("/api/v1/users/me/regions/recommendation")
    suspend fun getRecommendedUsers(): BaseResponse<GetRecommendedUsersResponse>

    @GET("/api/v1/users/me/regions")
    suspend fun getUserRegion(): BaseResponse<GetUserRegionResponse>

}
