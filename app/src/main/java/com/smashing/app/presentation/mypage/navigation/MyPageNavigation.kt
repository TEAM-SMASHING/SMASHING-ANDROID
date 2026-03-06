package com.smashing.app.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.login.navigation.Login
import com.smashing.app.presentation.mypage.MyPageRoute
import com.smashing.app.presentation.profile.navigation.navigateToMyProfile
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(MyPage, navOptions)
}

fun NavController.navigateToWithdraw(navOptions: NavOptions? = null) {
    this.navigate(Withdraw, navOptions)
}

fun NavGraphBuilder.myPageGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<MyPage> {
        val uriHandler = LocalUriHandler.current
        MyPageRoute(
            navigateUp = navController::navigateUp,
            navigateToMyProfile = navController::navigateToMyProfile,
            navigateToWithDraw = navController::navigateToWithdraw,
            navigateToLogout = {
                navController.navigate(Login) {
                    popUpTo<MyPage> { inclusive = true }
                    launchSingleTop = true
                }
            },
            navigateToPolicyPrivacy = {
                //TODO 실제 URL로 변경
                uriHandler.openUri("https://github.com/TEAM-SMASHING/SMASHING-ANDROID")
            },
            navigateToPolicyTerms = {
                //TODO 실제 URL로 변경
                uriHandler.openUri("https://github.com/TEAM-SMASHING/SMASHING-ANDROID")
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}


@Serializable
data object MyPage : Route

@Serializable
data object Withdraw : Route
