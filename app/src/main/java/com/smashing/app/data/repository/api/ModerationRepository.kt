package com.smashing.app.data.repository.api

import com.smashing.app.data.model.moderation.ReportSubmitResult

interface ModerationRepository {
    suspend fun postReportUser(
        reportedUserProfileId: String,
        reportTypeCode: String,
        reasonDetail: String?,
    ): ReportSubmitResult

    suspend fun postBlockUser(
        blockedUserProfileId: String,
    ): Result<Unit>
}
