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

    // 게임 상태 업데이트
    data class GameUpdated(
        val gameId: String,
        val submissionId: String?,
        val attemptNo: Int?,
        val resultStatus: GameResultStatusType,
    ) : SseEvent
}
