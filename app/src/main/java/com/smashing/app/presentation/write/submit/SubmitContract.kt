package com.smashing.app.presentation.write.submit

import androidx.compose.runtime.Immutable
import com.smashing.app.presentation.write.model.MatchPlayer

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
    )
}
