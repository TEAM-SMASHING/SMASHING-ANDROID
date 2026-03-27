package com.smashing.app.data.mapper.event

import com.smashing.app.data.model.event.Requester
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.remote.dto.event.GameUpdatedDto
import com.smashing.app.data.remote.dto.event.MatchingReceivedDto
import com.smashing.app.data.remote.dto.event.MatchingUpdatedDto
import com.smashing.app.data.remote.dto.event.common.RequesterDto
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType

fun RequesterDto.toDomain(): Requester =
    Requester(
        profileId = requesterProfileId,
        nickname = nickname,
        genderType = GenderType.findByName(gender),
        tierType = TierType.findTierType(tierCode),
        winCount = winCount,
        loseCount = loseCount,
        reviewCount = reviewCount,
    )

fun MatchingReceivedDto.toEvent(): SseEvent.MatchingReceived =
    SseEvent.MatchingReceived(
        matchingId = matchingId,
        sportType = SportType.findSportTypeToSportCode(sportCode),
        receiverProfileId = receiverProfileId,
        requester = requester.toDomain(),
    )

fun MatchingUpdatedDto.toEvent(): SseEvent.MatchingUpdated =
    SseEvent.MatchingUpdated(
        matchingId = matchingId,
        status = status,
    )

fun GameUpdatedDto.toEvent(): SseEvent.GameUpdated =
    SseEvent.GameUpdated(
        gameId = gameId,
        submissionId = submissionId,
        attemptNo = submissionAttemptNo,
        resultStatus = GameResultStatusType.findByResultStatus(resultStatus),
    )
