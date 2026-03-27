package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.ModerationRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.moderation.BlockUserRequest
import com.smashing.app.data.remote.dto.moderation.ReportUserRequest
import com.smashing.app.data.remote.service.ModerationService
import javax.inject.Inject

class ModerationRemoteDataSourceImpl @Inject constructor(
    private val moderationService: ModerationService,
) : ModerationRemoteDataSource {

    override suspend fun postReportUser(request: ReportUserRequest): BaseResponse<Unit> =
        moderationService.postReportUser(request)

    override suspend fun postBlockUser(request: BlockUserRequest): BaseResponse<Unit> =
        moderationService.postBlockUser(request)
}
