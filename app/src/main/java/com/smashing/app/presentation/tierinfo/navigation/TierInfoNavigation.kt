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
    tierInfo: String,
    navOptions: NavOptions? = null,
) = navigate(TierInfo(tierInfo = tierInfo), navOptions)

fun NavGraphBuilder.tierInfoGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<TierInfo> { backStackEntry ->

        TierInfoRoute(
            modifier = Modifier.padding(innerPadding),
            navigateUp = navController::navigateUp,
        )
    }
}

@Serializable
data class TierInfo(
    val tierInfo: String,
) : Route