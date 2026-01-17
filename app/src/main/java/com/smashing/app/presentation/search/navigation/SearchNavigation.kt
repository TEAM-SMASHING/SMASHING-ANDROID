package com.smashing.app.presentation.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.sharedViewModel
import com.smashing.app.presentation.home.navigation.navigateToRegionChange
import com.smashing.app.presentation.notice.navigation.navigateToNotice
import com.smashing.app.presentation.search.SearchViewModel
import com.smashing.app.presentation.search.input.SearchInputRoute
import com.smashing.app.presentation.search.searchmain.SearchMainRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) = navigate(Search, navOptions)


fun NavController.navigateToSearchInput(
    navOptions: NavOptions? = null
) = navigate(SearchInput, navOptions)

fun NavGraphBuilder.searchGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {
    navigation<Search>(
        startDestination = SearchMain,
    ) {
        composable<SearchMain> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SearchViewModel>(navController)

            SearchMainRoute(
                navigateToRegionChange = navController::navigateToRegionChange,
                navigateToNotice = navController::navigateToNotice,
                navigateToSearchInput = navController::navigateToSearchInput,
                modifier = Modifier,
                viewModel = viewModel,
            )
        }

        composable<SearchInput> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SearchViewModel>(navController)

            SearchInputRoute(
                navigateToSearchMain = navController::navigateUp,
                modifier = Modifier,
                viewModel = viewModel,
            )
        }
    }
}

@Serializable
data object Search : MainTabRoute

@Serializable
data object SearchMain : MainTabRoute

@Serializable
data object SearchInput : Route
