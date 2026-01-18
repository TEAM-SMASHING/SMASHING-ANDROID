package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.ranking.RankingListResponse

interface RankingRemoteDataSource {
    suspend fun getRankingList(): BaseResponse<RankingListResponse>
}