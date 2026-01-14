package com.smashing.app.presentation.ranking.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.ranking.RankingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateRankingPage(
    navOptions: NavOptions? = null,
){
    navigate(RankingPage)
}

fun NavGraphBuilder.rankingPage(
    paddingValues: PaddingValues,
    navController: NavController,
) {
    composable<RankingPage> { backStackEntry ->
        RankingRoute(
            modifier = Modifier,
        )
    }
}

@Serializable
data object RankingPage : Route