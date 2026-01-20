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
import com.smashing.app.presentation.profile.myprofile.MyProfileRoute
import com.smashing.app.presentation.profile.review.AllReviewRoute
import com.smashing.app.presentation.profile.userprofile.UserProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMyProfile(
    navOptions: NavOptions? = null,
) = navigate(Profile, navOptions)


fun NavController.navigateToUserProfile(
    userId: String,
    sportCode: String? = null,
    navOptions: NavOptions? = null,
) = navigate(UserProfile(userId, sportCode), navOptions)

fun NavController.navigateToReview(
    userId: String? = null,
    navOptions: NavOptions? = null,
) = navigate(Review(userId), navOptions)


fun NavGraphBuilder.profileGraph(
    navigateUp: () -> Unit,
    navigateToReview: (String?) -> Unit,
    navigateToAddSports: () -> Unit,
    innerPadding: PaddingValues,
    updateBottomBar: (Boolean) -> Unit,
) {
    navigation<Profile>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile> {
            MyProfileRoute(
                navigateToSportAdd = navigateToAddSports,
                navigateToTierGuide = {},
                navigateToReview = navigateToReview,
                updateBottomBar = updateBottomBar,
            )
        }
        composable<UserProfile>{
            UserProfileRoute(
                navigateToReview = navigateToReview,
            )
        }
        composable<Review> {
            AllReviewRoute(
                modifier = Modifier.padding(innerPadding),
                navigateUp = navigateUp,
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
    val isUser: Boolean = true,
) : Route


