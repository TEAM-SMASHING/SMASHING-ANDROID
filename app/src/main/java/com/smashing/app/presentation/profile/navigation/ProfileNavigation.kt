package com.smashing.app.presentation.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.clearBackStackWithRestoreNavOptions
import com.smashing.app.presentation.addsports.navigation.navigateToAddSports
import com.smashing.app.presentation.matching.navigation.navigateToMatching
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.profile.myprofile.MyProfileRoute
import com.smashing.app.presentation.profile.review.AllReviewRoute
import com.smashing.app.presentation.profile.userprofile.UserProfileRoute
import com.smashing.app.presentation.report.navigation.navigateToReport
import com.smashing.app.presentation.tierinfo.navigation.navigateToTierInfo
import kotlinx.serialization.Serializable

fun NavController.navigateToMyProfile(
    navOptions: NavOptions? = clearBackStackWithRestoreNavOptions(),
) = navigate(Profile, navOptions)


fun NavController.navigateToUserProfile(
    userId: String,
    sportCode: String? = null,
    navOptions: NavOptions? = null,
) = navigate(UserProfile(userId, sportCode), navOptions)

fun NavController.navigateToReview(
    userId: String? = null,
    sportCode: String? = null,
    navOptions: NavOptions? = null,
) = navigate(Review(userId, sportCode), navOptions)


fun NavGraphBuilder.profileGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    navigation<Profile>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile> {
            MyProfileRoute(
                navigateToSportAdd = navController::navigateToAddSports,
                navigateToReview = navController::navigateToReview,
                navigateToTierInfo = { tierInfoStyle, sportType ->
                    navController.navigateToTierInfo(
                        tierName = tierInfoStyle.name,
                        sportName = sportType.name,
                    )
                },
                modifier = Modifier.padding(innerPadding),
            )
        }

        composable<UserProfile> { backStackEntry ->
            val userProfile = backStackEntry.toRoute<UserProfile>()
            UserProfileRoute(
                navigateToReview = navController::navigateToReview,
                navigateUp = navController::navigateUp,
                navigateToSentMatching = {
                    navController.navigateToMatching(
                        initTab = MatchingType.SEND,
                    )
                },
                navigateToReport = {
                    navController.navigateToReport(userProfile.userId)
                },
            )
        }

        composable<Review> {
            AllReviewRoute(
                navigateUp = navController::navigateUp,
            )
        }
    }
}

@Serializable
data object Profile : MainTabRoute

@Serializable
data object MyProfile : MainTabRoute

@Serializable
data class UserProfile(
    val userId: String,
    val sportCode: String?,
) : Route

@Serializable
data class Review(
    val userId: String?,
    val sportCode: String?,
    val isUser: Boolean = true,
) : Route
