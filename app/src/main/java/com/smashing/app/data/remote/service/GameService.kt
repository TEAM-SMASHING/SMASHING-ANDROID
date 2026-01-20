package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GameService {
    @POST("/api/v1/games/{gameId}/submissions")
    suspend fun postGameSubmission(
        @Path("gameId")
        gameId: String,
        @Body request: PostGameSubmissionRequest,
    ): BaseResponse<PostGameSubmissionResponse>
}
