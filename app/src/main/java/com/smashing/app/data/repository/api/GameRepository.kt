package com.smashing.app.data.repository.api

import com.smashing.app.data.model.game.GameSubmission

interface GameRepository {

    suspend fun postGameSubmission(
        gameId: String,
        gameSubmission: GameSubmission,
    ): Result<String?>
}
