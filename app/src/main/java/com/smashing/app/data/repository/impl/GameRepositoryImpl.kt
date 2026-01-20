package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.game.toModel
import com.smashing.app.data.mapper.game.toRequest
import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.data.remote.datasource.api.GameRemoteDataSource
import com.smashing.app.data.remote.dto.game.PostRejectSubmissionRequest
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

    override suspend fun postConfirmSubmission(
        gameId: String,
        submissionId: String,
        submissionConfirm: SubmissionConfirm,
    ): Result<Unit> = suspendRunCatching {
        gameRemoteDataSource.postConfirmSubmission(
            gameId = gameId,
            submissionId = submissionId,
            request = submissionConfirm.toRequest(),
        ).requireData()
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
        reason: String,
    ): Result<Unit> = suspendRunCatching {
        gameRemoteDataSource.postRejectSubmission(
            gameId = gameId,
            submissionId = submissionId,
            request = PostRejectSubmissionRequest(reason = reason),
        ).requireData()
    }
}
