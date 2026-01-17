package com.smashing.app.presentation.write.confirm

import androidx.compose.runtime.Immutable
import com.smashing.app.data.type.ReviewRatingType
import com.smashing.app.data.type.ReviewTagType
import com.smashing.app.presentation.write.model.MatchPlayer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

interface ConfirmContract {
    @Immutable
    data class State(
        val submitter: MatchPlayer = MatchPlayer("", ""),
        val receiver: MatchPlayer = MatchPlayer("", ""),
        val submitterScore: Int = 3,
        val receiverScore: Int = 1,
        val winner: MatchPlayer? = null,
        val loser: MatchPlayer? = null,
        val isButtonEnabled: Boolean = false,
        val selectedRatingTypes: ImmutableSet<ReviewRatingType> = persistentSetOf(),
        val selectedTagTypes: ImmutableSet<ReviewTagType> = persistentSetOf(),
        val reviewText: String = "",

        val rating: String = "",
        val reviewerNickname: String = "",
        val revieweeNickname: String = "",
        val tag: ImmutableList<String> = persistentListOf(
            "FAIR_PLAY",
            "GOOD_MANNER"
        ),
        val content: String? = null,
    )
}
