package com.smashing.app.data.remote.mock.matching

import com.smashing.app.data.remote.dto.common.BaseResponse
import com.smashing.app.data.remote.dto.matching.GetMatchingListResponse
import com.smashing.app.data.remote.dto.matching.ReceivedMatchingSummaryDto
import com.smashing.app.data.remote.dto.matching.RequesterSummaryDto
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

// TODO: Mock 추후 삭제

object ReceivedMatchingsMockData {

    private val mockRequesterList = listOf(
        RequesterSummaryDto(
            userId = "user_001",
            nickname = "윤서",
            gender = "FEMALE",
            tierId = 10,
            reviewCount = 12,
            winCount = 18,
            loseCount = 7,
        ),
        RequesterSummaryDto(
            userId = "user_002",
            nickname = "민준",
            gender = "MALE",
            tierId = 6,
            reviewCount = 8,
            winCount = 10,
            loseCount = 5,
        ),
        RequesterSummaryDto(
            userId = "user_003",
            nickname = "지우",
            gender = "MALE",
            tierId = 3,
            reviewCount = 4,
            winCount = 5,
            loseCount = 3,
        ),
        RequesterSummaryDto(
            userId = "user_004",
            nickname = "서연",
            gender = "FEMALE",
            tierId = 8,
            reviewCount = 15,
            winCount = 22,
            loseCount = 10,
        ),
        RequesterSummaryDto(
            userId = "user_005",
            nickname = "도윤",
            gender = "MALE",
            tierId = 12,
            reviewCount = 20,
            winCount = 30,
            loseCount = 8,
        ),
    )

    private val mockMatchingList: List<ReceivedMatchingSummaryDto> = mockRequesterList.mapIndexed { index, requester ->
        ReceivedMatchingSummaryDto(
            matchingId = "matching_00${index + 1}",
            createdAt = OffsetDateTime.now().minusHours(index.toLong()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            status = "REQUESTED",
            requester = requester,
        )
    }

    fun mockReceivedMatchingResponse(
        hasNext: Boolean = false,
        nextCursor: String? = null,
    ): BaseResponse<GetMatchingListResponse> = BaseResponse(
        status = "SUCCESS",
        statusCode = 200,
        data = GetMatchingListResponse(
            snapshotAt = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            matchingList = mockMatchingList,
            nextCursor = nextCursor,
            hasNext = hasNext,
        ),
        timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
    )
}
