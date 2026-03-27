package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.model.moderation.ReportSubmitResult
import com.smashing.app.data.remote.datasource.api.ModerationRemoteDataSource
import com.smashing.app.data.remote.dto.moderation.BlockUserRequest
import com.smashing.app.data.remote.dto.moderation.ReportUserRequest
import com.smashing.app.data.repository.api.ModerationRepository
import retrofit2.HttpException
import javax.inject.Inject

class ModerationRepositoryImpl @Inject constructor(
    private val moderationRemoteDataSource: ModerationRemoteDataSource,
) : ModerationRepository {

    override suspend fun postReportUser(
        reportedUserProfileId: String,
        reportTypeCode: String,
        reasonDetail: String?,
    ): ReportSubmitResult =
        suspendRunCatching {
            moderationRemoteDataSource.postReportUser(
                ReportUserRequest(
                    reportedUserProfileId = reportedUserProfileId,
                    reportType = reportTypeCode,
                    reasonDetail = reasonDetail,
                ),
            )
        }.fold(
            onSuccess = { ReportSubmitResult.Success },
            onFailure = { throwable ->
                if (throwable is HttpException && throwable.code() == HTTP_STATUS_ALREADY_REPORTED) {
                    ReportSubmitResult.AlreadyReported
                } else {
                    ReportSubmitResult.Failure(throwable)
                }
            },
        )

    override suspend fun postBlockUser(
        blockedUserProfileId: String,
    ): Result<Unit> = suspendRunCatching {
        moderationRemoteDataSource.postBlockUser(
            BlockUserRequest(
                blockedUserProfileId = blockedUserProfileId,
            ),
        )
    }

    companion object {
        private const val HTTP_STATUS_ALREADY_REPORTED = 409
    }
}
