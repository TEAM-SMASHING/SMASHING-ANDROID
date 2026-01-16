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

fun NavController.navigateToRanking(
    navOptions: NavOptions? = null,
) { navigate(RankingPage, navOptions) }

fun NavGraphBuilder.rankingGraph(
    navigateUp: () -> Unit,
    innerPadding: PaddingValues,
) {
    composable<RankingPage> {
        RankingRoute(
            modifier = Modifier,
            navigateUp = navigateUp,
            //TODO 클릭했을 때, 대상의 프로필로 이동
            navigateToProfile = {},
        )
    }
}

@Serializable
data object RankingPage : Route