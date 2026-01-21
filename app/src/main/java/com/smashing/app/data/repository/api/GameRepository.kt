package com.smashing.app.data.repository.api

import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm

interface GameRepository {

    suspend fun postGameSubmission(
        gameId: String,
        gameSubmission: GameSubmission,
    ): Result<String?>

    suspend fun postConfirmSubmission(
        gameId: String,
        submissionId: String,
        submissionConfirm: SubmissionConfirm,
    ): Result<String>

    suspend fun getGameSubmission(
        gameId: String,
        submissionId: String,
    ): Result<GameSubmissionDetail>

    suspend fun postRejectSubmission(
        gameId: String,
        submissionId: String,
        reason: String,
    ): Result<Unit>
}
