package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.game.GetGameSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse
import com.smashing.app.data.type.ConfirmDenyType

interface GameRemoteDataSource {

    suspend fun postGameSubmission(
        gameId: String,
        request: PostGameSubmissionRequest,
    ): BaseResponse<PostGameSubmissionResponse>

    suspend fun postConfirmSubmission(
        gameId: String,
        submissionId: String,
        request: PostConfirmSubmissionRequest?,
    ): BaseResponse<PostConfirmSubmissionResponse>

    suspend fun getGameSubmission(
        gameId: String,
        submissionId: String,
    ): BaseResponse<GetGameSubmissionResponse>

    suspend fun postRejectSubmission(
        gameId: String,
        submissionId: String,
        reason: ConfirmDenyType?,
    ): BaseResponse<Unit>
}
