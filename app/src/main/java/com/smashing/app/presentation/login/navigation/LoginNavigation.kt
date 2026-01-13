package com.smashing.app.presentation.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) = navigate(Login, navOptions)

fun NavGraphBuilder.loginGraph(
    navigateToSignUp: (String) -> Unit,
    navigateToHome: () -> Unit,
    innerPadding: PaddingValues,
) {
    composable<Login> {
        LoginRoute(
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Login : Route
