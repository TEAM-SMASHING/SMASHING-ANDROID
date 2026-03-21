package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.report.ReportUserRequest

interface ReportRemoteDataSource {
    suspend fun postReportUser(request: ReportUserRequest): BaseResponse<Unit?>
}
