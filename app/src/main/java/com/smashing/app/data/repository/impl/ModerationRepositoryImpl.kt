package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.remote.datasource.api.ModerationRemoteDataSource
import com.smashing.app.data.remote.dto.moderation.BlockUserRequest
import com.smashing.app.data.remote.dto.moderation.ReportUserRequest
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.ModerationRepository
import javax.inject.Inject

class ModerationRepositoryImpl @Inject constructor(
    private val moderationRemoteDataSource: ModerationRemoteDataSource,
) : ModerationRepository {

    override suspend fun postReportUser(
        reportedUserProfileId: String,
        reportTypeCode: String,
        reasonDetail: String?,
    ): Result<Unit> = suspendRunCatching {
        moderationRemoteDataSource.postReportUser(
            ReportUserRequest(
                reportedUserProfileId = reportedUserProfileId,
                reportType = reportTypeCode,
                reasonDetail = reasonDetail,
            ),
        )
    }

    override suspend fun postBlockUser(
        blockedUserProfileId: String,
    ): Result<Unit> = suspendRunCatching {
        moderationRemoteDataSource.postBlockUser(
            BlockUserRequest(
                blockedUserProfileId = blockedUserProfileId,
            ),
        )
    }
}
