package com.smashing.app.data.remote.dto.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class GetMyRecentReviewStatsResponse(
    @SerialName("ratingCounts")
    val ratingCounts: RatingCounts,
    @SerialName("tagCounts")
    val tagCounts: TagCounts,
) {
    @Serializable
    data class RatingCounts(
        @SerialName("best")
        val best: Long,
        @SerialName("good")
        val good: Long,
        @SerialName("bad")
        val bad: Long,
    )

    @Serializable
    data class TagCounts(
        @SerialName("goodManner")
        val goodManner: Long,
        @SerialName("onTime")
        val onTime: Long,
        @SerialName("fairPlay")
        val fairPlay: Long,
        @SerialName("fastResponse")
        val fastResponse: Long,
    )
}
