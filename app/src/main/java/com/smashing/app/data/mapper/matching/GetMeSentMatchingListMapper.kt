package com.smashing.app.data.mapper.matching

import com.smashing.app.data.model.cursor.Cursor
import com.smashing.app.data.model.cursor.CursorPage
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.data.remote.dto.cursor.CursorDto
import com.smashing.app.data.remote.dto.matching.SentMatchingListResponse
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.TierType

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
        profileId = receiver.profileId,
        nickname = receiver.nickname,
        genderType = GenderType.findByName(receiver.gender),
        tierType = TierType.findTierType(receiver.tierCode),
        reviewCount = receiver.reviewCount,
        winCount = receiver.winCount,
        loseCount = receiver.loseCount,
        createdAt = createdAt,
    )
}
