package com.smashing.app.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.presentation.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    innerPadding: PaddingValues,
    navigateToNotice: () -> Unit,
) {
    composable<Home> {
        HomeRoute(
            modifier = Modifier.padding(innerPadding),
            navigateToNotice = navigateToNotice,
        )
    }
}

@Serializable
data object Home : MainTabRoute
