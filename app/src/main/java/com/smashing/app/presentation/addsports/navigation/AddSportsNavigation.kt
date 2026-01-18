package com.smashing.app.presentation.addsports.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.addsports.AddSportsRoute
import kotlinx.serialization.Serializable


fun NavController.navigateToAddSports(
    navOptions: NavOptions? = null,
) = navigate(AddSports, navOptions)

fun NavGraphBuilder.addSportsGraph(
    navigateUp: () -> Unit,
) {
    composable<AddSports> {
        AddSportsRoute(
            modifier = Modifier.fillMaxSize(),
            navigateUp = navigateUp,
        )
    }
}

@Serializable
data object AddSports : Route
