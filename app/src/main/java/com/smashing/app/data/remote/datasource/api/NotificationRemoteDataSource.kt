package com.smashing.app.data.remote.datasource.api

import com.smashing.app.data.remote.dto.BaseResponse
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.notification.GetNotificationSportMatchResponse
import com.smashing.app.data.remote.dto.notification.NotificationSummaryResponse

interface NotificationRemoteDataSource {

    suspend fun getNotificationList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): BaseResponse<CursorDto<NotificationSummaryResponse>>

    suspend fun putNotificationRead(
        notificationId: String,
    ): BaseResponse<Unit>

    suspend fun getNotificationSportMatch(
        notificationId: String,
    ): BaseResponse<GetNotificationSportMatchResponse>
}
