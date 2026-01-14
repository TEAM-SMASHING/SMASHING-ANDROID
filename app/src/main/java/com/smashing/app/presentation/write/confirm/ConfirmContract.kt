package com.smashing.app.presentation.write.confirm

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.type.ReviewRatingType
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

sealed interface ConfirmContract {
    @Immutable
    data class State(
        val selectedRatingTypes: ImmutableSet<ReviewRatingType> = persistentSetOf(),
    )
}
