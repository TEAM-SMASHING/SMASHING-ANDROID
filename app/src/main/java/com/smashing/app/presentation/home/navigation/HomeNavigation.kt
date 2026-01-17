package com.smashing.app.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.home.HomeRoute
import com.smashing.app.presentation.home.regionchange.RegionChangeRoute
import com.smashing.app.presentation.notice.navigation.navigateToNotice
import com.smashing.app.presentation.ranking.navigation.navigateToRanking
import com.smashing.app.presentation.region.navigation.getRegionResult
import com.smashing.app.presentation.region.navigation.navigateToRegion
import com.smashing.app.presentation.region.navigation.removeRegionResult
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(Home, navOptions)

fun NavController.navigateToRegionChange(
    navOptions: NavOptions? = null,
) = navigate(RegionChange, navOptions)

fun NavGraphBuilder.homeGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    navigation<Home>(
        startDestination = HomeUser,
    ) {
        composable<HomeUser> {
            HomeRoute(
                modifier = Modifier.padding(innerPadding),
                navigateToNotice = navController::navigateToNotice,
                navigateToRegionChange = navController::navigateToRegionChange,
                navigateToRanking = navController::navigateToRanking,
            )
        }

        composable<RegionChange> { backStackEntry ->
            val savedStateHandle = backStackEntry.savedStateHandle

            RegionChangeRoute(
                modifier = Modifier,
                navigateToRegion = navController::navigateToRegion,
                navigateUp = navController::navigateUp,
                regionResult = savedStateHandle.getRegionResult(),
                onRegionResultConsumed = savedStateHandle::removeRegionResult,
            )
        }
    }
}

@Serializable
data object Home : MainTabRoute

@Serializable
data object HomeUser : MainTabRoute

@Serializable
data object RegionChange : Route
