package com.smashing.app.presentation.report.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.report.ReportRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToReport(
    reportedUserId: String,
    navOptions: NavOptions? = null,
) = navigate(ReportPage(reportedUserId), navOptions)

fun NavGraphBuilder.reportGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<ReportPage> {
        ReportRoute(
            navigateUp = navController::navigateUp,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class ReportPage(
    val reportedUserId: String,
) : Route
