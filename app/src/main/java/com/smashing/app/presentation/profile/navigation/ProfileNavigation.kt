package com.smashing.app.presentation.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.profile.ProfileRoute
import com.smashing.app.presentation.profile.review.ReviewRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
) = navigate(Profile, navOptions)

fun NavController.navigateToReview(
    navOptions: NavOptions? = null,
) = navigate(Review, navOptions)

fun NavGraphBuilder.profileGraph(
    navigateUp: () -> Unit,
    navigateToReview: () -> Unit,
    innerPadding: PaddingValues,
    updateBottomBar: (Boolean) -> Unit,
) {
    navigation<Profile>(
        startDestination = ProfileUser,
    ) {
        composable<ProfileUser> {
            ProfileRoute(
                navigateToSportAdd = {},
                navigateToTierGuide = {},
                navigateToReviews = navigateToReview,
                updateBottomBar = updateBottomBar
            )
        }
        composable<Review> {
            ReviewRoute(
                modifier = Modifier.padding(innerPadding),
                navigateUp = navigateUp,
            )
        }
    }

}

@Serializable
data object Profile : MainTabRoute

@Serializable
data object ProfileUser : MainTabRoute

@Serializable
data object Review : Route
