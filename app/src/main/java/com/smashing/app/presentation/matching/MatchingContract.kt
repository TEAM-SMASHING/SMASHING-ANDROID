package com.smashing.app.presentation.matching

import androidx.compose.runtime.Immutable
import com.smashing.app.data.model.matching.AcceptedMatching
import com.smashing.app.data.model.matching.ReceivedMatching
import com.smashing.app.data.model.matching.SentMatching
import com.smashing.app.presentation.matching.type.MatchingType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface MatchingContract {
    @Immutable
    data class State(
        val selectedType: MatchingType = MatchingType.SEND,
        val receiveList: ImmutableList<ReceivedMatching> = persistentListOf(),
        val sendList: ImmutableList<SentMatching> = persistentListOf(),
        val acceptedList: ImmutableList<AcceptedMatching> = persistentListOf(),
    )
}
