package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.GameRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.game.GetGameSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostRejectSubmissionRequest
import com.smashing.app.data.remote.service.GameService
import com.smashing.app.presentation.write.confirm.type.ConfirmDenyType
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

    override suspend fun postConfirmSubmission(
        gameId: String,
        submissionId: String,
        request: PostConfirmSubmissionRequest,
    ): BaseResponse<PostConfirmSubmissionResponse> =
        gameService.postConfirmSubmission(
            gameId = gameId,
            submissionId = submissionId,
            request = request,
        )

    override suspend fun getGameSubmission(
        gameId: String,
        submissionId: String,
    ): BaseResponse<GetGameSubmissionResponse> =
        gameService.getGameSubmission(
            gameId = gameId,
            submissionId = submissionId,
        )

    override suspend fun postRejectSubmission(
        gameId: String,
        submissionId: String,
        reason: ConfirmDenyType?,
    ): BaseResponse<Unit> =
        gameService.postRejectSubmission(
            gameId = gameId,
            submissionId = submissionId,
            request = PostRejectSubmissionRequest(reason = reason),
        )
}
