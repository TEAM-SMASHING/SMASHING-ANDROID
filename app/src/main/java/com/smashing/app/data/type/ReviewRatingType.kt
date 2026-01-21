package com.smashing.app.data.type

enum class ReviewRatingType(
    val label: String,
) {
    BAD(
        label = "별로에요",
    ),
    GOOD(
        label = "좋아요",
    ),
    BEST(
        label = "최고에요",
    );

    companion object {
        fun findReviewRatingType(name: String): ReviewRatingType = entries.find { it.name == name } ?: ReviewRatingType.GOOD
    }
}
