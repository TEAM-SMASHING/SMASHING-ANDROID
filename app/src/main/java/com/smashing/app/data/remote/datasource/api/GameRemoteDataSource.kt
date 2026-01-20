package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse

interface GameRemoteDataSource {

    suspend fun postGameSubmission(
        gameId: String,
        request: PostGameSubmissionRequest,
    ): BaseResponse<PostGameSubmissionResponse>
}
