package com.smashing.app.data.mapper

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

fun CursorDto<ReceivedMatchingListResponse>.toDataModel(): CursorPage<ReceivedMatching> {
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
        genderType = GenderType.findByName(requester.gender) ?: GenderType.MALE,
        tierType = TierType.findTierType(requester.tierId) ?: TierType.IRON,
        reviewCount = requester.reviewCount,
        winCount = requester.winCount,
        loseCount = requester.loseCount,
        createdAt = createdAt,
    )
}

fun CursorDto<SentMatchingListResponse>.toDataModel(): CursorPage<SentMatching> {
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
        genderType = GenderType.findByName(receiver.gender) ?: GenderType.MALE,
        tierType = TierType.findTierType(receiver.tierId) ?: TierType.IRON,
        reviewCount = receiver.reviewCount,
        winCount = receiver.winCount,
        loseCount = receiver.loseCount,
        createdAt = createdAt,
    )
}
