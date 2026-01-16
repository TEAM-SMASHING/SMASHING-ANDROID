package com.smashing.app.presentation.search.input.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.search.input.SearchInputRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSearchInput(
    navOptions: NavOptions? = null
) = navigate(SearchInput, navOptions)

fun NavGraphBuilder.searchInputGraph(
    innerPadding: PaddingValues,
) {
    composable<SearchInput> {
        SearchInputRoute(
            modifier = Modifier
        )
    }
}

@Serializable
data object SearchInput : Route
