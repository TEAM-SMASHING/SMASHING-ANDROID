package com.smashing.app.data.mapper.game

import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.model.game.Review
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest
import com.smashing.app.data.remote.dto.game.PostGameSubmissionResponse

fun GameSubmission.toRequest(review: Review?): PostGameSubmissionRequest {
    return PostGameSubmissionRequest(
        winnerUserId = winnerUserId,
        loserUserId = loserUserId,
        winnerScore = winnerScore,
        loserScore = loserScore,
        review = review?.toDto(),
    )
}

private fun Review.toDto(): PostGameSubmissionRequest.Review {
    return PostGameSubmissionRequest.Review(
        rating = rating,
        content = content,
        tags = tags,
    )
}

fun PostGameSubmissionResponse.toReviewId(): String? = reviewId
