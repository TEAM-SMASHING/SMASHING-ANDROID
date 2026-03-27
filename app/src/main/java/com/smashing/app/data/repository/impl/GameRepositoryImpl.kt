package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.game.toModel
import com.smashing.app.data.mapper.game.toRequest
import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.data.remote.datasource.api.GameRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.remote.dto.toNetworkErrorResponse
import com.smashing.app.data.repository.api.GameRepository
import com.smashing.app.data.type.ConfirmDenyType
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameRemoteDataSource: GameRemoteDataSource,
    private val json: Json,
) : GameRepository {

    override suspend fun postGameSubmission(
        gameId: String,
        gameSubmission: GameSubmission,
    ): Result<String?> = suspendRunCatching {
        gameRemoteDataSource.postGameSubmission(
            gameId = gameId,
            request = gameSubmission.toRequest(),
        ).requireData().submissionId
    }.recoverCatching { error ->
        val networkError = error.toNetworkErrorResponse(json)
        throw IllegalStateException(networkError.message)
    }

    override suspend fun postConfirmSubmission(
        gameId: String,
        submissionId: String,
        submissionConfirm: SubmissionConfirm?,
    ): Result<String> = suspendRunCatching {
        gameRemoteDataSource.postConfirmSubmission(
            gameId = gameId,
            submissionId = submissionId,
            request = submissionConfirm?.toRequest(),
        ).requireData().reviewId
    }

    override suspend fun getGameSubmission(
        gameId: String,
        submissionId: String,
    ): Result<GameSubmissionDetail> = suspendRunCatching {
        gameRemoteDataSource.getGameSubmission(
            gameId = gameId,
            submissionId = submissionId,
        ).requireData().toModel()
    }

    override suspend fun postRejectSubmission(
        gameId: String,
        submissionId: String,
        reason: ConfirmDenyType?,
    ): Result<Unit> = suspendRunCatching {
        gameRemoteDataSource.postRejectSubmission(
            gameId = gameId,
            submissionId = submissionId,
            reason = reason,
        )
    }
}
