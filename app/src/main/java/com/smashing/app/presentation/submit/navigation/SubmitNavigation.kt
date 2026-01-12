package com.smashing.app.presentation.submit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.submit.SubmitRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSubmit(
    navOptions: NavOptions? = null
) = navigate(Submit, navOptions)

fun NavGraphBuilder.submitGraph(
    innerPadding: PaddingValues,
) {
    composable<Submit> {
        SubmitRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Submit : Route
