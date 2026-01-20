package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.toRankingData
import com.smashing.app.data.model.rank.Ranking
import com.smashing.app.data.remote.datasource.api.RankingRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.RankingRepository
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val rankingRemoteDataSource: RankingRemoteDataSource,
) : RankingRepository {

    override suspend fun getRankingList(): Result<Ranking> = suspendRunCatching {
        rankingRemoteDataSource.getRankingList()
            .requireData()
            .toRankingData()
    }
}