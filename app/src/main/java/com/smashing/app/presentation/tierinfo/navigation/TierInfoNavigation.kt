package com.smashing.app.presentation.tierinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.tierinfo.TierInfoRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToTierInfo(
    tierName: String,
    sportName: String,
    navOptions: NavOptions? = null,
) = navigate(SportTierInfo(tierName = tierName, sportName = sportName), navOptions)

fun NavGraphBuilder.tierInfoGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<SportTierInfo> { backStackEntry ->

        TierInfoRoute(
            modifier = Modifier.padding(innerPadding),
            navigateUp = navController::navigateUp,
        )
    }
}

@Serializable
data class SportTierInfo(
    val tierName: String,
    val sportName: String,
) : Route