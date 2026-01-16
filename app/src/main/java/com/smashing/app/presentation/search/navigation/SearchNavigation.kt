package com.smashing.app.presentation.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.presentation.search.SearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) = navigate(Search, navOptions)

fun NavGraphBuilder.searchGraph(
    innerPadding: PaddingValues,
    navigateToSearchInput: () -> Unit,
) {
    composable<Search> {
        SearchRoute(
            navigateToSearchInput = navigateToSearchInput,
            modifier = Modifier
        )
    }
}

@Serializable
data object Search : MainTabRoute
