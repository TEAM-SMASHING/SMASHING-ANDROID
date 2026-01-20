package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.game.GetGameSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostRejectSubmissionRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameService {
    @POST("/api/v1/games/{gameId}/submissions")
    suspend fun postGameSubmission(
        @Path("gameId")
        gameId: String,
        @Body request: PostGameSubmissionRequest,
    ): BaseResponse<PostGameSubmissionResponse>

    @POST("/api/v1/games/{gameId}/submissions/{submissionId}/confirm")
    suspend fun postConfirmSubmission(
        @Path("gameId")
        gameId: String,
        @Path("submissionId")
        submissionId: String,
        @Body request: PostConfirmSubmissionRequest?,
    ): BaseResponse<Unit>

    @GET("/api/v1/games/{gameId}/submissions/{submissionId}")
    suspend fun getGameSubmission(
        @Path("gameId")
        gameId: String,
        @Path("submissionId")
        submissionId: String,
    ): BaseResponse<GetGameSubmissionResponse>

    @POST("/api/v1/games/{gameId}/submissions/{submissionId}/reject")
    suspend fun postRejectSubmission(
        @Path("gameId")
        gameId: String,
        @Path("submissionId")
        submissionId: String,
        @Body request: PostRejectSubmissionRequest,
    ): BaseResponse<Unit>
}
