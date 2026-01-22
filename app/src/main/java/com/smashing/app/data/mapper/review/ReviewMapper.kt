package com.smashing.app.data.mapper.review

import com.smashing.app.data.model.review.ReviewDetail
import com.smashing.app.data.remote.dto.review.GetReviewResponse
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType

fun GetReviewResponse.toReviewDetail(): ReviewDetail {
    return ReviewDetail(
        rating = ReviewRatingType.findReviewRatingType(rating),
        reviewerNickname = reviewerNickname,
        revieweeNickname = revieweeNickname,
        tags = tag.mapNotNull { ReviewTagType.findReviewTagType(it) },
        content = content,
    )
}
