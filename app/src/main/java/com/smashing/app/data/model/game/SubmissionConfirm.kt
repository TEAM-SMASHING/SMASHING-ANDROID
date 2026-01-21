package com.smashing.app.data.model.game

import com.smashing.app.data.type.ReviewRatingType

data class SubmissionConfirm(
    val rating: ReviewRatingType,
    val content: String?,
    val tags: List<String>?,
)
