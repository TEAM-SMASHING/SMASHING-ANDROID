package com.smashing.app.data.mapper.game

import com.smashing.app.data.model.game.GameSubmission
import com.smashing.app.data.remote.dto.game.PostGameSubmissionRequest

fun GameSubmission.toRequest(): PostGameSubmissionRequest {
    return PostGameSubmissionRequest(
        winnerProfileId = winnerProfileId,
        loserProfileId = loserProfileId,
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
