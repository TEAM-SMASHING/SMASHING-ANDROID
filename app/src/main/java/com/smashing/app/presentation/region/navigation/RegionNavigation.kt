package com.smashing.app.presentation.region.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.region.RegionRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRegion(
    navOptions: NavOptions? = null
) = navigate(Region, navOptions)

fun NavGraphBuilder.regionGraph(
    innerPadding: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Region> {
        RegionRoute(
            modifier = Modifier.padding(innerPadding),
            navigateUp = navigateUp,
        )
    }
}

@Serializable
data object Region : Route