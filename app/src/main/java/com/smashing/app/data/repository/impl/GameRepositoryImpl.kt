package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.game.toRequest
import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.remote.datasource.api.GameRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameRemoteDataSource: GameRemoteDataSource,
) : GameRepository {

    override suspend fun postGameSubmission(
        gameId: String,
        gameSubmission: GameSubmission,
    ): Result<String?> = suspendRunCatching {
        gameRemoteDataSource.postGameSubmission(
            gameId = gameId,
            request = gameSubmission.toRequest(),
        ).requireData().reviewId
    }
}
