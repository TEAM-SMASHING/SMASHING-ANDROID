package com.smashing.app.data.repository.api

interface ReportRepository {
    suspend fun postReportUser(
        reportedUserId: String,
        reportTypeCode: String,
        reasonDetail: String?,
    ): Result<Unit>
}
