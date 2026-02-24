package com.smashing.app.presentation.confirmreview

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReviewRatingType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface ConfirmReviewContract {
    @Immutable
    data class State(
        val nickname: String = "",
        val reviewRatingType: ReviewRatingType = ReviewRatingType.GOOD,
        val reviewText: String? = null,
        val tags: ImmutableList<String> = persistentListOf(),
        val isLoading: Boolean = false,
    )
}
