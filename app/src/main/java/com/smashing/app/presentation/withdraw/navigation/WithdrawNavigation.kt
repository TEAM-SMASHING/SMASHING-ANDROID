package com.smashing.app.presentation.withdraw.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.clearBackStackNavOptions
import com.smashing.app.presentation.login.navigation.navigateToLogin
import com.smashing.app.presentation.withdraw.WithdrawRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToWithdraw(
    navOptions: NavOptions? = null,
) = navigate(Withdraw, navOptions)

fun NavGraphBuilder.withdrawGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Withdraw> {
        WithdrawRoute(
            modifier = Modifier.padding(innerPadding),
            navigateUp = navController::navigateUp,
            navigateToLogin = {
                navController.navigateToLogin(
                    navOptions = navController.clearBackStackNavOptions()
                )
            },
        )
    }
}

@Serializable
data object Withdraw : Route
