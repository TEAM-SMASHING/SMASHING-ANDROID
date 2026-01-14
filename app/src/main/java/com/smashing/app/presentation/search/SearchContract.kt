package com.smashing.app.presentation.search

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface SearchContract {
    @Immutable
    data class State(
        val searchList: ImmutableList<SearchItemModel> = persistentListOf()
    )
}
