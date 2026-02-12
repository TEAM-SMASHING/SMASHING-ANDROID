package com.smashing.app.data.mapper.game

import com.smashing.app.data.model.game.SubmissionConfirm
import com.smashing.app.data.remote.dto.game.PostConfirmSubmissionRequest
import com.smashing.app.data.remote.dto.game.ReviewRequest

fun SubmissionConfirm?.toRequest(): PostConfirmSubmissionRequest? {
    return this?.let {
        PostConfirmSubmissionRequest(
            review = ReviewRequest(
                rating = it.rating.name,
                content = it.content,
                tags = it.tags,
            )
        )
    }
}
