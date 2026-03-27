package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.moderation.BlockUserRequest
import com.smashing.app.data.remote.dto.moderation.ReportUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ModerationService {

    @POST("/api/v1/reports")
    suspend fun postReportUser(
        @Body request: ReportUserRequest,
    ): BaseResponse<Unit>

    @POST("/api/v1/users/block")
    suspend fun postBlockUser(
        @Body request: BlockUserRequest,
    ): BaseResponse<Unit>
}
