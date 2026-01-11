package com.smashing.app.presentation.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.presentation.profile.ProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null
) = navigate(Profile, navOptions)

fun NavGraphBuilder.profileGraph(
    innerPadding: PaddingValues,
) {
    composable<Profile> {
        ProfileRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Profile : MainTabRoute
