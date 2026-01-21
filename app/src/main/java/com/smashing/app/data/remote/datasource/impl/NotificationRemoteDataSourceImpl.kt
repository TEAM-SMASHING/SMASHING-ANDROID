package com.smashing.app.data.remote.datasource.impl

import com.smashing.app.data.remote.datasource.api.NotificationRemoteDataSource
import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.notification.NotificationSummaryResponse
import com.smashing.app.data.remote.service.NotificationService
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService,
) : NotificationRemoteDataSource {

    override suspend fun getNotificationList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): BaseResponse<CursorDto<NotificationSummaryResponse>> =
        notificationService.getNotificationList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
        )
}
