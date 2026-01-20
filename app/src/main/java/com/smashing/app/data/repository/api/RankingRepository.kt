package com.smashing.app.data.repository.api

import com.smashing.app.data.model.rank.Ranking

interface RankingRepository {
    suspend fun getRankingList(): Result<Ranking>
}