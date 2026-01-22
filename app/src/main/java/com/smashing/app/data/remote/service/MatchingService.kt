package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.AcceptedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.type.OrderType
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchingService {

    @GET("/api/v1/users/me/matchings/received")
    suspend fun getMeReceivedMatchingList(
        @Query("snapshotAt")
        snapshotAt: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Long?,
    ): BaseResponse<CursorDto<ReceivedMatchingListResponse>>

    @GET("/api/v1/users/me/matchings/sent")
    suspend fun getMeSentMatchingList(
        @Query("snapshotAt")
        snapshotAt: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Long?,
    ): BaseResponse<CursorDto<SentMatchingListResponse>>

    @GET("/api/v1/users/me/games/pending-results")
    suspend fun getMeAcceptedMatchingList(
        @Query("snapshotAt")
        snapshotAt: String?,
        @Query("cursor")
        cursor: String?,
        @Query("size")
        size: Long?,
        @Query("order")
        order: OrderType?,
    ): BaseResponse<CursorDto<AcceptedMatchingListResponse>>

    @POST("/api/v1/matchings/{matchingId}/accept")
    suspend fun postAcceptedMatching(
        @Path("matchingId")
        matchingId: String,
    ): BaseResponse<Unit>

    @DELETE("/api/v1/matchings/{matchingId}")
    suspend fun deleteSentMatching(
        @Path("matchingId")
        matchingId: String,
    ): BaseResponse<Unit>

    @POST("/api/v1/matchings/{matchingId}/reject")
    suspend fun postRejectMatching(
        @Path("matchingId")
        matchingId: String,
    ): BaseResponse<Unit>

    @PUT("/api/v1/games/{gameId}")
    suspend fun putCancelGame(
        @Path("gameId")
        gameId: String,
    ): BaseResponse<Unit>

    @POST("/api/v1/matchings/profiles/{receiverProfileId}")
    suspend fun postMatching(
        @Path("receiverProfileId")
        receiverProfileId: String,
    ): BaseResponse<Unit>
}
