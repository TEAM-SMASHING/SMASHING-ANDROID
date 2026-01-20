package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.RankingRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.ranking.RankingListResponse
import com.smashing.app.data.remote.service.RankingService
import javax.inject.Inject

class RankingRemoteDataSourceImpl @Inject constructor(
    private val rankingService: RankingService,
) : RankingRemoteDataSource {

    override suspend fun getRankingList(): BaseResponse<RankingListResponse> =
        rankingService.getRankingList()
}