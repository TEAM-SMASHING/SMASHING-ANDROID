package com.smashing.app.presentation.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.confirmreview.navigation.navigateToConfirmReview
import com.smashing.app.presentation.matching.navigation.navigateToMatching
import com.smashing.app.presentation.notice.NoticeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToNotice(
    profileId: String,
    navOptions: NavOptions? = null
) = navigate(Notice(profileId = profileId), navOptions)

fun NavGraphBuilder.noticeGraph(
    navController: NavController,
) {
    composable<Notice> {
        NoticeRoute(
            navigateUp = navController::navigateUp,
            navigateToMatching = { initialTab ->
                navController.navigateToMatching(
                    initTab = initialTab,
                )
            },
            navigateToConfirmReview = navController::navigateToConfirmReview,
        )
    }
}

@Serializable
data class Notice(
    val profileId: String,
) : Route
