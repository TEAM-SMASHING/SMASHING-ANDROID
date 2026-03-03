package com.smashing.app.presentation.matching.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.smashing.app.presentation.matching.type.MatchingType

private object MatchingArgs {
    const val INIT_TAB = "matching_init_tab"
}

fun NavController.setMatchingArgs(tab: MatchingType?) {
    getBackStackEntry(Matching).savedStateHandle[MatchingArgs.INIT_TAB] = tab
}

fun SavedStateHandle.getMatchingArgs(): MatchingType? {
    return get<MatchingType>(MatchingArgs.INIT_TAB)
}

fun SavedStateHandle.removeMatchingArgs() {
    remove<MatchingType>(MatchingArgs.INIT_TAB)
}
