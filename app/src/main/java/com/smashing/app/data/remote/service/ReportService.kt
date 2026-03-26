package com.smashing.app.data.remote.service

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.report.BlockUserRequest
import com.smashing.app.data.remote.dto.report.ReportUserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {

    @POST("/api/v1/reports")
    suspend fun postReportUser(
        @Body request: ReportUserRequest,
    ): BaseResponse<Unit?>

    @POST("/api/v1/users/block")
    suspend fun postBlockUser(
        @Body request: BlockUserRequest,
    ): BaseResponse<Unit?>
}
