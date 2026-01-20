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
    ): Result<Unit>

    suspend fun getGameSubmission(
        gameId: String,
        submissionId: String,
    ): Result<GameSubmissionDetail>
}
