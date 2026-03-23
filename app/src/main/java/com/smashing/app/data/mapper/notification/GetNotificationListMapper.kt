package com.smashing.app.data.mapper.notification

import com.smashing.app.core.util.ConvertTimeProvider.calculateNotificationTime
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.notification.NotificationSummaryResponse
import com.smashing.app.data.type.NotificationType.REVIEW_RECEIVED
import com.smashing.app.domain.model.Notification

fun CursorDto<NotificationSummaryResponse>.toNotificationList(): CursorPage<Notification> {
    return CursorPage(
        items = results.map { it.toNotification() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun NotificationSummaryResponse.toNotification(): Notification {
    val reviewId =
        if (notificationType == REVIEW_RECEIVED) extractReviewIdFromLinkUrl(linkUrl) else null

    return Notification(
        notificationId = notificationId,
        notificationType = notificationType,
        title = title,
        description = content,
        isRead = isRead,
        timeAgo = calculateNotificationTime(createdAt),
        reviewId = reviewId,
    )
}

private fun extractReviewIdFromLinkUrl(linkUrl: String): String? {
    val prefix = "/api/v1/reviews/"
    if (!linkUrl.startsWith(prefix)) return null

    val id = linkUrl.removePrefix(prefix).substringBefore("/")
    return id.takeIf { it.isNotBlank() }
}
