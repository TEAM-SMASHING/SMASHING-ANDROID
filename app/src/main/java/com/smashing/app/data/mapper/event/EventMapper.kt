package com.smashing.app.data.mapper.event

import com.smashing.app.data.model.event.Requester
import com.smashing.app.data.model.event.SseEvent
import com.smashing.app.data.model.event.UserSummary
import com.smashing.app.data.type.GameResultStatusType
import com.smashing.app.data.type.GenderType
import com.smashing.app.data.type.SportType
import com.smashing.app.data.type.TierType
import com.smashing.app.data.remote.dto.event.GameResultRejectedNotificationDto
import com.smashing.app.data.remote.dto.event.GameResultSubmittedNotificationDto
import com.smashing.app.data.remote.dto.event.GameUpdatedDto
import com.smashing.app.data.remote.dto.event.MatchingAcceptNotificationDto
import com.smashing.app.data.remote.dto.event.MatchingReceivedDto
import com.smashing.app.data.remote.dto.event.MatchingRequestNotificationDto
import com.smashing.app.data.remote.dto.event.MatchingUpdatedDto
import com.smashing.app.data.remote.dto.event.ReviewReceivedNotificationDto
import com.smashing.app.data.remote.dto.event.common.RequesterDto
import com.smashing.app.data.remote.dto.event.common.UserSummaryDto


fun UserSummaryDto.toDomain(): UserSummary =
    UserSummary(
        userId = userId,
        nickname = nickname,
        tierType = TierType.findTierType(tierCode),
    )

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

fun MatchingRequestNotificationDto.toEvent(): SseEvent.MatchingRequestNotification =
    SseEvent.MatchingRequestNotification(
        notificationId = notificationId,
        notificationType = notificationType,
        notificationCreatedAt = notificationCreatedAt,
        matchingId = matchingId,
        sportType = SportType.findSportType(sportId),
        receiverProfileId = receiverProfileId,
        requester = requester.toDomain(),
    )

fun MatchingAcceptNotificationDto.toEvent(): SseEvent.MatchingAcceptNotification =
    SseEvent.MatchingAcceptNotification(
        notificationId = notificationId,
        notificationType = notificationType,
        notificationCreatedAt = notificationCreatedAt,
        matchingId = matchingId,
        sportType = SportType.findSportType(sportId),
        receiverProfileId = receiverProfileId,
        acceptor = acceptor.toDomain(),
    )

fun GameUpdatedDto.toEvent(): SseEvent.GameUpdated =
    SseEvent.GameUpdated(
        gameId = gameId,
        submissionId = submissionId,
        attemptNo = submissionAttemptNo,
        resultStatus = GameResultStatusType.findByResultStatus(resultStatus),
    )

fun GameResultSubmittedNotificationDto.toEvent(): SseEvent.GameResultSubmittedNotification =
    SseEvent.GameResultSubmittedNotification(
        notificationId = notificationId,
        notificationType = notificationType,
        notificationCreatedAt = notificationCreatedAt,
        sportType = SportType.findSportType(sportId),
        receiverProfileId = receiverProfileId,
        gameId = gameId,
        submissionId = submissionId,
        submitter = submitter.toDomain(),
    )

fun GameResultRejectedNotificationDto.toEvent(): SseEvent.GameResultRejectedNotification =
    SseEvent.GameResultRejectedNotification(
        notificationId = notificationId,
        notificationType = notificationType,
        notificationCreatedAt = notificationCreatedAt,
        sportType = SportType.findSportType(sportId),
        receiverProfileId = receiverProfileId,
        gameId = gameId,
        rejector = rejector.toDomain(),
    )

fun ReviewReceivedNotificationDto.toEvent(): SseEvent.ReviewReceivedNotification =
    SseEvent.ReviewReceivedNotification(
        notificationId = notificationId,
        notificationType = notificationType,
        notificationCreatedAt = notificationCreatedAt,
        sportType = SportType.findSportType(sportId),
        receiverProfileId = receiverProfileId,
        gameId = gameId,
        reviewId = reviewId,
        reviewer = reviewer.toDomain(),
    )
