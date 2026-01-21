package com.smashing.app.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.addsports.navigation.navigateToAddSports
import com.smashing.app.presentation.home.HomeRoute
import com.smashing.app.presentation.home.regionchange.RegionChangeRoute
import com.smashing.app.presentation.matching.navigation.navigateToMatching
import com.smashing.app.presentation.matching.type.MatchingType
import com.smashing.app.presentation.notice.navigation.navigateToNotice
import com.smashing.app.presentation.profile.navigation.navigateToUserProfile
import com.smashing.app.presentation.ranking.navigation.navigateToRanking
import com.smashing.app.presentation.region.navigation.getRegionResult
import com.smashing.app.presentation.region.navigation.navigateToRegion
import com.smashing.app.presentation.region.navigation.removeRegionResult
import com.smashing.app.presentation.search.navigation.navigateToSearch
import com.smashing.app.presentation.tierinfo.navigation.navigateToTierInfo
import com.smashing.app.presentation.write.navigation.navigateToConfirm
import com.smashing.app.presentation.write.navigation.navigateToSubmit
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(Home, navOptions)

fun NavController.navigateToRegionChange(
    navOptions: NavOptions? = null,
) = navigate(RegionChange, navOptions)

fun NavGraphBuilder.homeGraph(
    innerPadding: PaddingValues,
    navController: NavController,
    updateBottomBar: (Boolean) -> Unit,
) {
    navigation<Home>(
        startDestination = HomeUser,
    ) {
        composable<HomeUser> {
            HomeRoute(
                modifier = Modifier,
                navigateToNotice = navController::navigateToNotice,
                navigateToRegionChange = navController::navigateToRegionChange,
                navigateToRanking = navController::navigateToRanking,
                navigateToTierInfo = { tierInfoStyle, sportType ->
                    navController.navigateToTierInfo(tierName = tierInfoStyle.name, sportName = sportType.sportName)
                },
                navigateToMatchingAccepted = {
                    navController.navigateToMatching(initTab = MatchingType.ACCEPTED)
                },
                navigateToUserProfile = { userId ->
                    navController.navigateToUserProfile(userId = userId)
                },
                navigateToSportAdd = navController::navigateToAddSports,
                navigateToSearch = navController::navigateToSearch,
                navigateToSubmit = { gameId, opponentUserId, opponentNickname, isFirstAttempt ->
                    navController.navigateToSubmit(
                        gameId = gameId,
                        opponentUserId = opponentUserId,
                        opponentNickname = opponentNickname,
                        isFirstAttempt = isFirstAttempt,
                    )
                },
                navigateToConfirm = { submissionId, gameId , isFirstAttempt ->
                    navController.navigateToConfirm(
                        submissionId = submissionId,
                        gameId = gameId,
                        isFirstAttempt = isFirstAttempt,
                    )
                },
                updateBottomBar = updateBottomBar,
            )
        }

        composable<RegionChange> { backStackEntry ->
            val savedStateHandle = backStackEntry.savedStateHandle

            RegionChangeRoute(
                modifier = Modifier,
                navigateToRegion = navController::navigateToRegion,
                navigateUp = navController::navigateUp,
                regionResult = savedStateHandle.getRegionResult(),
                onRegionResultConsumed = savedStateHandle::removeRegionResult,
            )
        }
    }
}

@Serializable
data object Home : MainTabRoute

@Serializable
data object HomeUser : MainTabRoute

@Serializable
data object RegionChange : Route
