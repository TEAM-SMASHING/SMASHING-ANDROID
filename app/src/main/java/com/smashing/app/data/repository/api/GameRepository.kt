package com.smashing.app.data.repository.api

import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest

interface GameRepository {

    suspend fun postGameSubmission(
        gameId: String,
        request: PostGameSubmissionRequest,
    ): Result<String?>
}
