package com.smashing.app.data.mapper.game

import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.GameSubmissionDetail
import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.data.remote.dto.game.GetGameSubmissionResponse
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.ReviewRequest

fun GameSubmission.toRequest(): PostGameSubmissionRequest {
    return PostGameSubmissionRequest(
        winnerUserId = winnerUserId,
        loserUserId = loserUserId,
        winnerScore = winnerScore,
        loserScore = loserScore,
        review = review?.toDto(),
    )
}

private fun GameSubmission.Review.toDto(): PostGameSubmissionRequest.Review {
    return PostGameSubmissionRequest.Review(
        rating = rating,
        content = content,
        tags = tags,
    )
}

fun SubmissionConfirm.toRequest(): PostConfirmSubmissionRequest {
    return PostConfirmSubmissionRequest(
        review = ReviewRequest(
            rating = rating,
            content = content,
            tags = tags,
        )
    )
}

fun GetGameSubmissionResponse.toModel(): GameSubmissionDetail {
    return GameSubmissionDetail(
        attemptNo = attemptNo,
        submitter = GameSubmissionDetail.SubmitterInfo(
            userId = submitter.userId,
            nickname = submitter.nickname,
        ),
        winner = GameSubmissionDetail.PlayerInfo(
            userId = winner.userId,
            nickname = winner.nickname,
            score = winner.score,
        ),
        loser = GameSubmissionDetail.PlayerInfo(
            userId = loser.userId,
            nickname = loser.nickname,
            score = loser.score,
        ),
    )
}
