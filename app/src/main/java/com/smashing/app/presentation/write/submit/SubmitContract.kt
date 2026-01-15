package com.smashing.app.presentation.write.submit

import androidx.compose.runtime.Immutable
import com.smashing.app.core.common.type.ReviewRatingType
import com.smashing.app.core.common.type.ReviewTagType
import com.smashing.app.presentation.write.model.MatchPlayer
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

interface SubmitContract {
    @Immutable
    data class State(
        val submitter: MatchPlayer = MatchPlayer("", ""),
        val receiver: MatchPlayer = MatchPlayer("", ""),
        val submitterScore: Int = 0,
        val receiverScore: Int = 0,
        val winner: MatchPlayer? = null,
        val loser: MatchPlayer? = null,
        val isButtonEnabled: Boolean = false,
        val selectedRatingTypes: ImmutableSet<ReviewRatingType> = persistentSetOf(),
        val selectedTagTypes: ImmutableSet<ReviewTagType> = persistentSetOf(),
        val reviewText: String = "",
    )
}
