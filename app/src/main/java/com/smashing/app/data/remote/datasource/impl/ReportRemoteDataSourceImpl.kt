package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.ReportRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.report.ReportUserRequest
import com.smashing.app.data.remote.service.ReportService
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportRemoteDataSource {

    override suspend fun postReportUser(request: ReportUserRequest): BaseResponse<Unit?> =
        reportService.postReportUser(request)
}
