package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.moderation.BlockUserRequest
import com.smashing.app.data.remote.dto.moderation.ReportUserRequest

interface ModerationRemoteDataSource {
    suspend fun postReportUser(request: ReportUserRequest): BaseResponse<Unit?>
    suspend fun postBlockUser(request: BlockUserRequest): BaseResponse<Unit?>
}
