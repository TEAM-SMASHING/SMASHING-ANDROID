package com.smashing.app.presentation.write.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.sharedViewModel
import com.smashing.app.presentation.write.confirm.ConfirmResultRoute
import com.smashing.app.presentation.write.confirm.ConfirmReviewRoute
import com.smashing.app.presentation.write.confirm.ConfirmViewModel
import com.smashing.app.presentation.write.submit.SubmitResultRoute
import com.smashing.app.presentation.write.submit.SubmitReviewRoute
import com.smashing.app.presentation.write.submit.SubmitViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToSubmit(
    gameId: String,
    navOptions: NavOptions? = null,
) = navigate(Submit(gameId = gameId), navOptions)

fun NavController.navigateToConfirm(
    gameId: String,
    navOptions: NavOptions? = null,
) = navigate(Confirm(gameId = gameId), navOptions)

fun NavController.navigateToSubmitReview(
    navOptions: NavOptions? = null,
) = navigate(SubmitReview, navOptions)

fun NavController.navigateToConfirmReview(
    navOptions: NavOptions? = null,
) = navigate(ConfirmReview, navOptions)


fun NavGraphBuilder.writeGraph(
    navigateToMatching: () -> Unit,
    navController: NavHostController,
) {
    navigation<Submit>(
        startDestination = SubmitResult,
    ) {
        composable<SubmitResult> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SubmitViewModel>(navController)

            SubmitResultRoute(
                navigateUp = navController::navigateUp,
                navigateToSubmitReview = navController::navigateToSubmitReview,
                viewModel = viewModel,
            )
        }

        composable<SubmitReview> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SubmitViewModel>(navController)

            SubmitReviewRoute(
                navigateUp = navController::navigateUp,
                navigateToMatching = navigateToMatching,
                viewModel = viewModel,
            )
        }
    }

    navigation<Confirm>(
        startDestination = ConfirmResult,
    ) {
        composable<ConfirmResult> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<ConfirmViewModel>(navController)

            ConfirmResultRoute(
                navigateUp = navController::navigateUp,
                navigateToConfirmReview = navController::navigateToConfirmReview,
                navigateToMatching = navigateToMatching,
                viewModel = viewModel,
            )
        }

        composable<ConfirmReview> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<ConfirmViewModel>(navController)

            ConfirmReviewRoute(
                navigateUp = navController::navigateUp,
                navigateToMatching = navigateToMatching,
                viewModel = viewModel,
            )
        }

    }
}

@Serializable
data class Submit(
    val gameId: String,
) : Route

@Serializable
data object SubmitResult : Route

@Serializable
data object SubmitReview : Route

@Serializable
data class Confirm(
    val gameId: String,
) : Route

@Serializable
data object ConfirmResult : Route

@Serializable
data object ConfirmReview : Route
