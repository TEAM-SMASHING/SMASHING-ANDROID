package com.smashing.app.presentation.addsports.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.addsports.AddSportsRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToAddSports(
    navOptions: NavOptions? = null,
) = navigate(AddSports, navOptions)

fun NavGraphBuilder.addSportsGraph(
    navController: NavController,
) {
    composable<AddSports> {
        AddSportsRoute(
            navigateUp = navController::navigateUp,
        )
    }
}

@Serializable
data object AddSports : Route
