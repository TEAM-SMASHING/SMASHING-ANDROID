package com.smashing.app.presentation.signup.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.extension.clearBackStackOptions
import com.smashing.app.presentation.home.navigation.navigateToHome
import com.smashing.app.presentation.region.navigation.getRegionResult
import com.smashing.app.presentation.region.navigation.navigateToRegion
import com.smashing.app.presentation.region.navigation.removeRegionResult
import com.smashing.app.presentation.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSignUp(
    kakaoId: String,
    navOptions: NavOptions? = null,
) = navigate(SignUp(kakaoId), navOptions)

fun NavGraphBuilder.signUpGraph(
    navController: NavController,
) {
    composable<SignUp> { backStackEntry ->
        val savedStateHandle = backStackEntry.savedStateHandle

        SignUpRoute(
            navigateUp = navController::navigateUp,
            regionResult = savedStateHandle.getRegionResult(),
            onRegionResultConsumed = savedStateHandle::removeRegionResult,
            navigateToRegion = navController::navigateToRegion,
            navigateToHome = {
                navController.navigateToHome(navOptions = clearBackStackOptions())
            },
            modifier = Modifier,
        )
    }
}

@Serializable
data class SignUp(
    val kakaoId: String,
)
