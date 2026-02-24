package com.smashing.app.data.model.event

import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.MatchingStatusType
import com.smashing.app.data.type.NotificationType
import com.smashing.app.data.type.SportType

sealed interface SseEvent {

    data object SystemConnected : SseEvent

    // 매칭 요청 받음
    data class MatchingReceived(
        val matchingId: String,
        val sportType: SportType,
        val receiverProfileId: String,
        val requester: Requester,
    ) : SseEvent

    // 매칭 상태 업데이트 (ACCEPTED, REJECTED, CANCELLED)
    data class MatchingUpdated(
        val matchingId: String,
        val status: MatchingStatusType,
    ) : SseEvent

    // 매칭 요청 알림
    data class MatchingRequestNotification(
        val notificationId: String,
        val notificationType: NotificationType,
        val notificationCreatedAt: String,
        val matchingId: String,
        val sportType: SportType,
        val receiverProfileId: String,
        val requester: UserSummary,
    ) : SseEvent

    // 매칭 수락 알림
    data class MatchingAcceptNotification(
        val notificationId: String,
        val notificationType: NotificationType,
        val notificationCreatedAt: String,
        val matchingId: String,
        val sportType: SportType,
        val receiverProfileId: String,
        val acceptor: UserSummary,
    ) : SseEvent

    // 게임 상태 업데이트
    data class GameUpdated(
        val gameId: String,
        val submissionId: String?,
        val attemptNo: Int?,
        val resultStatus: GameResultStatusType,
    ) : SseEvent

    // 게임 결과 제출 알림
    data class GameResultSubmittedNotification(
        val notificationId: String,
        val notificationType: NotificationType,
        val notificationCreatedAt: String,
        val sportType: SportType,
        val receiverProfileId: String,
        val gameId: String,
        val submissionId: String,
        val submitter: UserSummary,
    ) : SseEvent

    // 게임 결과 거절 알림
    data class GameResultRejectedNotification(
        val notificationId: String,
        val notificationType: NotificationType,
        val notificationCreatedAt: String,
        val sportType: SportType,
        val receiverProfileId: String,
        val gameId: String,
        val rejector: UserSummary,
    ) : SseEvent

    // 후기 수신 알림
    data class ReviewReceivedNotification(
        val notificationId: String,
        val notificationType: NotificationType,
        val notificationCreatedAt: String,
        val sportType: SportType,
        val receiverProfileId: String,
        val gameId: String,
        val reviewId: String,
        val reviewer: UserSummary,
    ) : SseEvent
}
