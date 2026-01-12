package com.smashing.app.presentation.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.presentation.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSignUp(
    authId: String,
    navOptions: NavOptions? = null,
) = navigate(SignUp(authId), navOptions)

fun NavGraphBuilder.signUpGraph(
    navigateToHome: () -> Unit,
    innerPadding: PaddingValues,
) {
    composable<SignUp> {
        SignUpRoute(
            innerPadding = innerPadding,
            navigateToHome = navigateToHome,
        )
    }
}

@Serializable
data class SignUp(
    val authId: String,
)
