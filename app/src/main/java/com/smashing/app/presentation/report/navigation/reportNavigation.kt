package com.smashing.app.presentation.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.report.ReportRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToReport(
    navOptions: NavOptions? = null,
) = navigate(ReportPage, navOptions)

fun NavGraphBuilder.reportGraph(
    navController: NavController,
) {
    composable<ReportPage> {
        ReportRoute(
            navigateUp = navController::navigateUp,
        )
    }
}

@Serializable
data object ReportPage : Route
