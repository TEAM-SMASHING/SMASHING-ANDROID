package com.smashing.app.data.mapper.matching

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.AcceptedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

fun CursorDto<ReceivedMatchingListResponse>.toReceivedMatchingList(): CursorPage<ReceivedMatching> {
    return CursorPage(
        items = results.map { it.toReceivedMatching() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun ReceivedMatchingListResponse.toReceivedMatching(): ReceivedMatching {
    return ReceivedMatching(
        matchingId = matchingId,
        userId = requester.userId,
        nickname = requester.nickname,
        genderType = GenderType.findByName(requester.gender),
        tierType = TierType.findTierType(requester.tierCode),
        reviewCount = requester.reviewCount,
        winCount = requester.winCount,
        loseCount = requester.loseCount,
        createdAt = createdAt,
    )
}

fun CursorDto<SentMatchingListResponse>.toSentMatchingList(): CursorPage<SentMatching> {
    return CursorPage(
        items = results.map { it.toSentMatching() },
        cursor = Cursor(
            snapshotAt = snapshotAt,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
    )
}

private fun SentMatchingListResponse.toSentMatching(): SentMatching {
    return SentMatching(
        matchingId = matchingId,
        userId = receiver.userId,
        nickname = receiver.nickname,
        genderType = GenderType.findByName(receiver.gender),
        tierType = TierType.findTierType(receiver.tierCode),
        reviewCount = receiver.reviewCount,
        winCount = receiver.winCount,
        loseCount = receiver.loseCount,
        createdAt = createdAt,
    )
}

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
    return AcceptedMatching(
        gameId = gameId,
        resultStatus = GameResultStatusType.findByResultStatus(resultStatus),
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
    )
}
