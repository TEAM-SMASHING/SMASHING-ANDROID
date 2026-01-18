package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.ranking.RankingListResponse
import retrofit2.http.GET

interface RankingService {

    @GET("/api/v1/users/me/regions/leaderboard")
    suspend fun getRankingList(): BaseResponse<RankingListResponse>
}