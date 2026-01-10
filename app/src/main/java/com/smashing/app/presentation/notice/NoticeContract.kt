package com.smashing.app.presentation.notice

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.type.NotificationType
import com.smashing.app.core.common.type.SportType
import com.smashing.app.domain.model.Notification
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

// TODO 추후 삭제 예정
private val mockList = List(20) { index ->
    Notification(
        notificationId = index.toString(),
        title = "알림 제목 $index",
        description = "이것은 $index 번째 알림 설명입니다.",
        notificationType = if (index % 2 == 0) NotificationType.MATCHING_ACCEPTED else NotificationType.RESULT_REJECTED_SCORE_MISMATCH,
        userId = "user_$index",
        sportType = if (index % 2 == 0) SportType.TENNIS else SportType.PING_PONG,
        isRead = index > 5,
        timeAgo = "${index}분 전",
    )
}.toPersistentList()

interface NoticeContract {

    @Immutable
    data class State(
        val noticeList: ImmutableList<Notification> = mockList,
    )
}
