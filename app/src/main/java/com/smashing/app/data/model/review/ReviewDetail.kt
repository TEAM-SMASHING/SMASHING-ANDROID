package com.smashing.app.data.model.review

import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType

data class ReviewDetail(
    val rating: ReviewRatingType,
    val reviewerNickname: String,
    val revieweeNickname: String,
    val tags: List<ReviewTagType>,
    val content: String?,
)
