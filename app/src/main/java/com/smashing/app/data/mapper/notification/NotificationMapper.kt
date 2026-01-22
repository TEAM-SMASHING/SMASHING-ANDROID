package com.smashing.app.data.mapper.notification

import com.smashing.app.core.util.ConvertTimeProvider
import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.notification.NotificationSummaryResponse
import com.smashing.app.data.type.SportType
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
    return Notification(
        notificationId = notificationId,
        userId = receiverProfileId,
        nickname = senderNickName,
        sportType = SportType.findSportType(receiverSportId),
        notificationType = notificationType,
        title = title,
        description = content,
        isRead = isRead,
        timeAgo = ConvertTimeProvider.convertLocalDateTimeToTime(createdAt),
        linkUrl = linkUrl,
        relatedId = extractIdFromLinkUrl(linkUrl),
    )
}

private fun extractIdFromLinkUrl(linkUrl: String): String? {
    return linkUrl.substringAfterLast("/", "").takeIf { it.isNotEmpty() }
}
