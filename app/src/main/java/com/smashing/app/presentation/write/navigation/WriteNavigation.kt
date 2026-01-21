package com.smashing.app.presentation.write.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.sharedViewModel
import com.smashing.app.presentation.confirmreview.navigation.navigateToConfirmReview
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.write.confirm.ConfirmResultRoute
import com.smashing.app.presentation.write.confirm.ConfirmReviewRoute
import com.smashing.app.presentation.write.confirm.ConfirmViewModel
import com.smashing.app.presentation.write.submit.SubmitResultRoute
import com.smashing.app.presentation.write.submit.SubmitReviewRoute
import com.smashing.app.presentation.write.submit.SubmitViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToSubmit(
    gameId: String,
    opponentUserId: String,
    opponentNickname: String,
    isFirstAttempt: Boolean,
    navOptions: NavOptions? = null,
) = navigate(
    route = Submit(
        gameId = gameId,
        opponentUserId = opponentUserId,
        opponentNickname = opponentNickname,
        isFirstAttempt = isFirstAttempt,
    ),
    navOptions = navOptions,
)

fun NavController.navigateToConfirm(
    submissionId: String,
    gameId: String,
    isFirstAttempt: Boolean,
    navOptions: NavOptions? = null,
) = navigate(
    route = Confirm(
        submissionId = submissionId,
        gameId = gameId,
        isFirstAttempt = isFirstAttempt,
    ),
    navOptions = navOptions,
)

fun NavController.navigateToSubmitReview(
    navOptions: NavOptions? = null,
) = navigate(SubmitReview, navOptions)

fun NavController.navigateToConfirmReview(
    navOptions: NavOptions? = null,
) = navigate(ConfirmReview, navOptions)


fun NavGraphBuilder.writeGraph(
    navigateToMatching: (initTab: MatchingType) -> Unit,
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
                navigateToMatching = { navigateToMatching(MatchingType.ACCEPTED) },
                viewModel = viewModel,
            )
        }

        composable<SubmitReview> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SubmitViewModel>(navController)

            SubmitReviewRoute(
                navigateUp = navController::navigateUp,
                navigateToMatching = { navigateToMatching(MatchingType.ACCEPTED) },
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
                viewModel = viewModel,
            )
        }

        composable<ConfirmReview> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<ConfirmViewModel>(navController)

            ConfirmReviewRoute(
                navigateUp = navController::navigateUp,
                navigateToConfirmReview = { reviewId ->
                    navController.navigateToConfirmReview(
                        reviewId = reviewId,
                        navOptions = navOptions {
                            popUpTo<ConfirmReview> {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    )
                },
                viewModel = viewModel,
            )
        }

    }
}

@Serializable
data class Submit(
    val gameId: String,
    val opponentUserId: String,
    val opponentNickname: String,
    val isFirstAttempt: Boolean,
) : Route

@Serializable
data object SubmitResult : Route

@Serializable
data object SubmitReview : Route

@Serializable
data class Confirm(
    val submissionId: String,
    val gameId: String,
    val isFirstAttempt: Boolean,
) : Route

@Serializable
data object ConfirmResult : Route

@Serializable
data object ConfirmReview : Route
