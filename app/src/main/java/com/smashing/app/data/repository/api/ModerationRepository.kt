package com.smashing.app.data.repository.api

interface ModerationRepository {
    suspend fun postReportUser(
        reportedUserId: String,
        reportTypeCode: String,
        reasonDetail: String?,
    ): Result<Unit>

    suspend fun postBlockUser(
        blockedUserProfileId: String,
    ): Result<Unit>
}
