package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.remote.datasource.api.ReportRemoteDataSource
import com.smashing.app.data.remote.dto.report.BlockUserRequest
import com.smashing.app.data.remote.dto.report.ReportUserRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportRemoteDataSource: ReportRemoteDataSource,
) : ReportRepository {

    override suspend fun postReportUser(
        reportedUserId: String,
        reportTypeCode: String,
        reasonDetail: String?,
    ): Result<Unit> = suspendRunCatching {
        reportRemoteDataSource.postReportUser(
            ReportUserRequest(
                reportedUserId = reportedUserId,
                reportType = reportTypeCode,
                reasonDetail = reasonDetail,
            ),
        ).requireData()
    }

    override suspend fun postBlockUser(
        blockedUserProfileId: String,
    ): Result<Unit> = suspendRunCatching {
        reportRemoteDataSource.postBlockUser(
            BlockUserRequest(
                blockedUserProfileId = blockedUserProfileId,
            ),
        )
    }
}
