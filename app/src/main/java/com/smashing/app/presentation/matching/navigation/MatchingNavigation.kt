package com.smashing.app.presentation.matching.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.presentation.matching.MatchingRoute
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.profile.navigation.navigateToUserProfile
import com.smashing.app.presentation.write.navigation.navigateToConfirm
import com.smashing.app.presentation.write.navigation.navigateToSubmit
import kotlinx.serialization.Serializable

fun NavController.navigateToMatching(
    initTab: MatchingType = MatchingType.RECEIVE,
    navOptions: NavOptions? = null,
) = navigate(Matching(initTab = initTab), navOptions)

fun NavGraphBuilder.matchingGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Matching> {
        MatchingRoute(
            navigateToSubmit = navController::navigateToSubmit,
            navigateToConfirm = navController::navigateToConfirm,
            navigateToProfile = navController::navigateToUserProfile,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Serializable
data class Matching(
    val initTab: MatchingType = MatchingType.RECEIVE,
) : MainTabRoute
