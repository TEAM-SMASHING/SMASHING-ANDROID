package com.smashing.app.presentation.splash.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.home.navigation.navigateToHome
import com.smashing.app.presentation.login.navigation.navigateToLogin
import com.smashing.app.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    navigation<Splash>(
        startDestination = Splash,
    ) {
        composable<Splash> {
            SplashRoute(
                navigateToLogin = navController::navigateToLogin,
                navigateToHome = navController::navigateToHome,
            )
        }
    }
}

@Serializable
data object Splash : Route
