package com.smashing.app.presentation.confirmreview.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.confirmreview.ConfirmReviewRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToConfirmReview(
    reviewId: String,
    navOptions: NavOptions? = null,
) = navigate(
    route = ConfirmReview(reviewId = reviewId),
    navOptions = navOptions,
)

fun NavGraphBuilder.confirmReviewGraph(
    navigateUp: () -> Unit,
) {
    composable<ConfirmReview> {
        ConfirmReviewRoute(
            navigateUp = navigateUp,
        )
    }
}

@Serializable
data class ConfirmReview(
    val reviewId: String,
) : Route
