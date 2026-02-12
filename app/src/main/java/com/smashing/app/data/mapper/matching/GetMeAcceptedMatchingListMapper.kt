package com.smashing.app.data.mapper.matching

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.AcceptedMatchingListResponse
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType


fun CursorDto<AcceptedMatchingListResponse>.toAcceptedMatchingList(): CursorPage<AcceptedMatching> {
    return CursorPage(
        items = results.map { it.toAcceptedMatching() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun AcceptedMatchingListResponse.toAcceptedMatching(): AcceptedMatching {
    val currentStatus = GameResultStatusType.findByResultStatus(resultStatus)
    val isSubmittedByMe = latestSubmitterId != null && latestSubmitterId != opponentSummary.userId

    val resultStatus = when {
        isSubmittedByMe &&
                currentStatus == GameResultStatusType.WAITING_CONFIRMATION ->
            GameResultStatusType.PENDING_RESULT_CONFIRMED

        !isSubmittedByMe &&
                currentStatus == GameResultStatusType.RESULT_REJECTED ->
            GameResultStatusType.PENDING_RESULT_CONFIRMED

        else -> currentStatus
    }


    return AcceptedMatching(
        gameId = gameId,
        resultStatus = resultStatus,
        createdAt = createdAt,
        userId = opponentSummary.userId,
        nickname = opponentSummary.nickname,
        openChatUrl = opponentSummary.openChatUrl,
        genderType = GenderType.findByName(opponentSummary.gender),
        tierType = TierType.findTierType(opponentSummary.tierCode),
        submitAvailableAt = submitAvailableAt,
        remainingSeconds = remainingSeconds,
        isSubmitLocked = isSubmitLocked,
        latestSubmissionId = latestSubmissionId,
        latestAttemptNo = latestAttemptNo,
        latestSubmitterId = latestSubmitterId,
    )
}
