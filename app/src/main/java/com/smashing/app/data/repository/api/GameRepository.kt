package com.smashing.app.data.repository.api

import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.presentation.write.confirm.type.ConfirmDenyType

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
        reason: ConfirmDenyType?,
    ): Result<Unit>
}
