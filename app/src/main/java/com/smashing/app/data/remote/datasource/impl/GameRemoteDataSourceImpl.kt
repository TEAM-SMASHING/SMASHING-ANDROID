package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.GameRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse
import com.smashing.app.data.remote.service.GameService
import javax.inject.Inject

class GameRemoteDataSourceImpl @Inject constructor(
    private val gameService: GameService,
) : GameRemoteDataSource {

    override suspend fun postGameSubmission(
        gameId: String,
        request: PostGameSubmissionRequest,
    ): BaseResponse<PostGameSubmissionResponse> =
        gameService.postGameSubmission(
            gameId = gameId,
            request = request,
        )

}
