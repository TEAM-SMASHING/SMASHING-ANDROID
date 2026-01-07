package com.smashing.app.data.mapper

import com.smashing.app.core.common.type.TierType
import com.smashing.app.data.model.common.CursorPage
import com.smashing.app.data.model.matching.ReceivedMatchingItem
import com.smashing.app.data.model.matching.RequesterSummary
import com.smashing.app.data.remote.dto.matching.GetMatchingListResponse
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingSummaryDto
import com.smashing.app.data.remote.dto.matching.RequesterSummaryDto
import java.time.OffsetDateTime

fun GetMatchingListResponse.toCursorPage(): CursorPage<ReceivedMatchingItem> = CursorPage(
    items = matchingList.map { it.toModel() },
    nextCursor = nextCursor,
    hasNext = hasNext,
    snapshotAt = snapshotAt,
)

fun ReceivedMatchingSummaryDto.toModel(): ReceivedMatchingItem = ReceivedMatchingItem(
    matchingId = matchingId,
    createdAt = OffsetDateTime.parse(createdAt),
    status = status,
    requesterSummary = requester.toModel(),
)

fun RequesterSummaryDto.toModel(): RequesterSummary = RequesterSummary(
    userId = userId,
    nickname = nickname,
    gender = gender,
    tierType = TierType.findTierType(tierId),
    winCount = winCount,
    loseCount = loseCount,
    reviewCount = reviewCount,
)
