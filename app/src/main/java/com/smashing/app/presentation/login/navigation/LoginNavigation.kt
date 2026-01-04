package com.smashing.app.presentation.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.home.HomeRoute
import com.smashing.app.presentation.home.navigation.Home
import com.smashing.app.presentation.home.navigation.navigateToHome
import com.smashing.app.presentation.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) = navigate(Login, navOptions)

fun NavGraphBuilder.loginGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Login> {
        LoginRoute(
            navigateToHome = navController::navigateToHome,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Login : Route
