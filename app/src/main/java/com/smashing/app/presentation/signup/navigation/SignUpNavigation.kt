package com.smashing.app.presentation.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.presentation.region.navigation.getRegionResult
import com.smashing.app.presentation.region.navigation.removeRegionResult
import com.smashing.app.presentation.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSignUp(
    kakaoId: String,
    navOptions: NavOptions? = null,
) = navigate(SignUp(kakaoId), navOptions)

fun NavGraphBuilder.signUpGraph(
    navigateToRegion: () -> Unit,
    navigateToHome: () -> Unit,
    innerPadding: PaddingValues,
) {
    composable<SignUp> {backStackEntry ->
        val savedStateHandle = backStackEntry.savedStateHandle

        SignUpRoute(
            regionResult = savedStateHandle.getRegionResult(),
            onRegionResultConsumed = savedStateHandle::removeRegionResult,
            navigateToRegion = navigateToRegion,
            navigateToHome = navigateToHome,
            modifier = Modifier,
        )
    }
}

@Serializable
data class SignUp(
    val kakaoId: String,
)
