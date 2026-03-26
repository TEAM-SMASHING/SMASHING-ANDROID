package com.smashing.app.presentation.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.clearBackStackNavOptions
import com.smashing.app.presentation.home.navigation.navigateToHome
import com.smashing.app.presentation.login.navigation.navigateToLogin
import com.smashing.app.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashGraph(
    navController: NavController,
) {
    composable<Splash> {
        SplashRoute(
            navigateToLogin = {
                navController.navigateToLogin(
                    navOptions = navController.clearBackStackNavOptions()
                )
            },
            navigateToHome = navController::navigateToHome,
        )
    }
}

@Serializable
data object Splash : Route
