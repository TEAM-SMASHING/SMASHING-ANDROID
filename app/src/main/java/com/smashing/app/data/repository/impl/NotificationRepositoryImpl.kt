package com.smashing.app.data.repository.impl

import com.smashing.app.core.util.suspendRunCatching
import com.smashing.app.data.mapper.notification.toNotificationList
import com.smashing.app.data.mapper.notification.toNotificationSportMatch
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.notification.NotificationSportMatch
import com.smashing.app.data.remote.datasource.api.NotificationRemoteDataSource
import com.smashing.app.data.remote.dto.requireData
import com.smashing.app.data.repository.api.NotificationRepository
import com.smashing.app.data.model.notification.Notification
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource,
) : NotificationRepository {

    override suspend fun getNotificationList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): Result<CursorPage<Notification>> = suspendRunCatching {
        notificationRemoteDataSource.getNotificationList(
            snapshotAt = snapshotAt,
            cursor = cursor,
            size = size,
        ).requireData().toNotificationList()
    }

    override suspend fun putNotificationRead(
        notificationId: String,
    ): Result<Unit> = suspendRunCatching {
        notificationRemoteDataSource.putNotificationRead(
            notificationId = notificationId,
        )
    }

    override suspend fun getNotificationSportMatch(
        notificationId: String,
    ): Result<NotificationSportMatch> = suspendRunCatching {
        notificationRemoteDataSource.getNotificationSportMatch(
            notificationId = notificationId,
        ).requireData().toNotificationSportMatch()
    }
}
