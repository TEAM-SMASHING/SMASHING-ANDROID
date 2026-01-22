package com.smashing.app.presentation.notice.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.notice.NoticeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToNotice(
    profileId: String,
    navOptions: NavOptions? = null
) = navigate(Notice(profileId = profileId), navOptions)

fun NavGraphBuilder.noticeGraph(
    innerPadding: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMatching: (MatchingType) -> Unit,
    navigateToConfirmReview: (String) -> Unit,
) {
    composable<Notice> { backStackEntry ->
        NoticeRoute(
            modifier = Modifier.padding(innerPadding),
            navigateUp = navigateUp,
            navigateToMatching = navigateToMatching,
            navigateToConfirmReview = navigateToConfirmReview,
        )
    }
}

@Serializable
data class Notice(
    val profileId: String,
) : Route
