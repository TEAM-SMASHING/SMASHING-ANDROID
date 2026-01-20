package com.smashing.app.presentation.notice

import androidx.lifecycle.ViewModel
import com.smashing.app.data.type.NotificationType
import com.smashing.app.data.type.SportType
import com.smashing.app.domain.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(getDummyState())
    val uiState = _uiState.asStateFlow()

    private fun getDummyState(): NoticeContract.State {
        // TODO 추후 삭제 예정
        val mockList = List(20) { index ->
            Notification(
                notificationId = index.toString(),
                title = "알림 제목 $index",
                description = "이것은 $index 번째 알림 설명입니다.",
                notificationType = if (index % 2 == 0) NotificationType.MATCHING_ACCEPTED else NotificationType.RESULT_REJECTED_SCORE_MISMATCH,
                userId = "user_$index",
                sportType = if (index % 2 == 0) SportType.TENNIS else SportType.PING_PONG,
                isRead = index > 5,
                nickname = "test",
                timeAgo = "${index}분 전",
            )
        }.toPersistentList()

        return NoticeContract.State(noticeList = mockList)
    }
}
