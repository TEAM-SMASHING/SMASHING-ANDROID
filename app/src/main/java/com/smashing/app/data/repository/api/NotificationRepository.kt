package com.smashing.app.data.repository.api

import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.notification.NotificationSportMatch
import com.smashing.app.domain.model.Notification

interface NotificationRepository {

    suspend fun getNotificationList(
        snapshotAt: String?,
        cursor: String?,
        size: Long?,
    ): Result<CursorPage<Notification>>

    suspend fun putNotificationRead(
        notificationId: String,
    ): Result<Unit>

    suspend fun getNotificationSportMatch(
        notificationId: String,
    ): Result<NotificationSportMatch>
}
