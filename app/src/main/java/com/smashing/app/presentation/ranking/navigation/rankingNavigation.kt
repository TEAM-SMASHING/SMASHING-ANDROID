package com.smashing.app.presentation.ranking.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.profile.navigation.navigateToMyProfile
import com.smashing.app.presentation.profile.navigation.navigateToUserProfile
import com.smashing.app.presentation.ranking.RankingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRanking(
    navOptions: NavOptions? = null,
) = navigate(RankingPage, navOptions)

fun NavGraphBuilder.rankingGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<RankingPage> {
        RankingRoute(
            modifier = Modifier,
            navigateUp = navController::navigateUp,
            navigateToProfile = { userId ->
                navController.navigateToUserProfile(userId = userId)
            },
            navigateToMyProfile = navController::navigateToMyProfile,
        )
    }
}

@Serializable
data object RankingPage : Route