package com.smashing.app.presentation.ranking.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.clearBackStackWithRestoreNavOptions
import com.smashing.app.presentation.profile.navigation.navigateToMyProfile
import com.smashing.app.presentation.profile.navigation.navigateToUserProfile
import com.smashing.app.presentation.ranking.RankingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRanking(
    navOptions: NavOptions? = null,
) = navigate(RankingPage, navOptions)

fun NavGraphBuilder.rankingGraph(
    navController: NavController,
) {
    composable<RankingPage> {
        RankingRoute(
            navigateUp = navController::navigateUp,
            navigateToProfile = navController::navigateToUserProfile,
            navigateToMyProfile = {
                navController.navigateToMyProfile(
                    navOptions = clearBackStackWithRestoreNavOptions(),
                )
            },
        )
    }
}

@Serializable
data object RankingPage : Route
