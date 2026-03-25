package com.smashing.app.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.core.extension.clearBackStackNavOptions
import com.smashing.app.presentation.login.navigation.Login
import com.smashing.app.presentation.mypage.MyPageRoute
import com.smashing.app.presentation.profile.navigation.navigateToMyProfile
import com.smashing.app.presentation.withdraw.navigation.navigateToWithdraw
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(MyPage, navOptions)
}

fun NavGraphBuilder.myPageGraph(
    navController: NavController,
) {
    composable<MyPage> {
        MyPageRoute(
            navigateUp = navController::navigateUp,
            navigateToMyProfile = navController::navigateToMyProfile,
            navigateToWithDraw = navController::navigateToWithdraw,
            navigateToLogin = {
                navController.navigate(
                    Login,
                    navController.clearBackStackNavOptions()
                )
            },
        )
    }
}


@Serializable
data object MyPage : Route
