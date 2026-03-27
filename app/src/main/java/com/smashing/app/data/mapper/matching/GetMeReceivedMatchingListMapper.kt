package com.smashing.app.data.mapper.matching

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingListResponse
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
        profileId = requester.profileId,
        nickname = requester.nickname,
        genderType = GenderType.findByName(requester.gender),
        tierType = TierType.findTierType(requester.tierCode),
        reviewCount = requester.reviewCount,
        winCount = requester.winCount,
        loseCount = requester.loseCount,
        createdAt = createdAt,
    )
}
