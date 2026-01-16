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
import com.smashing.app.presentation.region.navigation.navigateToRegion
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(Home, navOptions)

fun NavController.navigateToRegionChange(
    addressName: String? = null,
    cityName: String? = null,
    districtName: String? = null,
    navOptions: NavOptions? = null,
) = navigate(RegionChange(addressName, cityName, districtName), navOptions)

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
            )
        }

        composable<RegionChange> {
            RegionChangeRoute(
                modifier = Modifier.padding(innerPadding),
                navigateToRegion = navController::navigateToRegion,
                navigateUp = navController::navigateUp,
                //TODO Home 과정 수정 예정
                navigateToHome = navController::navigateUp,
            )
        }
    }
}

@Serializable
data object Home : MainTabRoute

@Serializable
data object HomeUser : MainTabRoute

@Serializable
data class RegionChange(
    val addressName: String?,
    val cityName: String?,
    val districtName: String?,
) : Route
