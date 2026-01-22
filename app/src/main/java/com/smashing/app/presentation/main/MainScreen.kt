package com.smashing.app.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.smashing.app.core.designsystem.component.toast.LocalToastTrigger
import com.smashing.app.core.designsystem.component.toast.SmashingToast
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.addsports.navigation.addSportsGraph
import com.smashing.app.presentation.confirmreview.navigation.confirmReviewGraph
import com.smashing.app.presentation.confirmreview.navigation.navigateToConfirmReview
import com.smashing.app.presentation.home.navigation.homeGraph
import com.smashing.app.presentation.home.navigation.navigateToHome
import com.smashing.app.presentation.login.navigation.Login
import com.smashing.app.presentation.login.navigation.loginGraph
import com.smashing.app.presentation.main.component.MainBottomBar
import com.smashing.app.presentation.matching.navigation.Matching
import com.smashing.app.presentation.matching.navigation.matchingGraph
import com.smashing.app.presentation.matching.navigation.navigateToMatching
import com.smashing.app.presentation.notice.navigation.Notice
import com.smashing.app.presentation.notice.navigation.noticeGraph
import com.smashing.app.presentation.profile.navigation.profileGraph
import com.smashing.app.presentation.profile.navigation.navigateToUserProfile
import com.smashing.app.presentation.ranking.navigation.rankingGraph
import com.smashing.app.presentation.region.navigation.navigateToRegion
import com.smashing.app.presentation.region.navigation.regionGraph
import com.smashing.app.presentation.search.navigation.searchGraph
import com.smashing.app.presentation.signup.navigation.navigateToSignUp
import com.smashing.app.presentation.signup.navigation.signUpGraph
import com.smashing.app.presentation.tierinfo.navigation.tierInfoGraph
import com.smashing.app.presentation.write.navigation.navigateToConfirm
import com.smashing.app.presentation.write.navigation.navigateToSubmit
import com.smashing.app.presentation.write.navigation.writeGraph
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

private const val EXIT_MILLIS = 3000L

@Composable
fun MainScreen(
    appState: MainAppState,
) {
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val snackbarMutex = remember { Mutex() }

    val coroutineScope = rememberCoroutineScope()
    val onShowToast: (String) -> Unit = remember(coroutineScope, snackBarHostState, snackbarMutex) {
        { message ->
            coroutineScope.launch {
                if (!snackbarMutex.tryLock()) return@launch

                try {
                    launch {
                        delay(EXIT_MILLIS)
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                    snackBarHostState.showSnackbar(
                        message = message,
                        withDismissAction = false,
                    )
                } finally {
                    snackbarMutex.unlock()
                }
            }
        }
    }

    CompositionLocalProvider(
        LocalToastTrigger provides onShowToast,
    ) {
        Scaffold(
            snackbarHost = {
                val bottomPadding by animateDpAsState(
                    targetValue = if (isBottomBarVisible) 20.dp else 46.dp,
                    label = "snackbar_bottom_padding"
                )
                SnackbarHost(hostState = snackBarHostState) { data ->
                    SmashingToast(
                        text = data.visuals.message,
                        modifier = Modifier
                            .padding(bottom = bottomPadding)
                            .padding(horizontal = 16.dp),
                    )
                }
            },
            bottomBar = {
                MainBottomBar(
                    isVisible = isBottomBarVisible,
                    tabs = MainTab.entries.toImmutableList(),
                    currentTab = currentTab,
                    onTabSelected = appState::navigate,
                )
            },
            containerColor = SmashingTheme.colors.bgCanvas,
            modifier = Modifier
                .fillMaxSize()
                .background(color = SmashingTheme.colors.bgCanvas),
        ) { innerPadding ->
            MainNavHost(
                appState = appState,
                innerPadding = innerPadding,
            )
        }
    }
}

@Composable
private fun MainNavHost(
    appState: MainAppState,
    innerPadding: PaddingValues,
) {
    NavHost(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        navController = appState.navController,
        startDestination = appState.startDestination,
    ) {
        homeGraph(
            navController = appState.navController,
            updateBottomBar = appState::updateBottomBarVisible,
        )

        searchGraph(
            navController = appState.navController,
            updateBottomBar = appState::updateBottomBarVisible,
        )

        matchingGraph(
            navigateToSubmit = appState.navController::navigateToSubmit,
            navigateToConfirm = appState.navController::navigateToConfirm,
            updateBottomBar = appState::updateBottomBarVisible,
            navigateToProfile = { userId ->
                appState.navController.navigateToUserProfile(userId = userId)
            },
        )

        profileGraph(
            navController = appState.navController,
            updateBottomBar = appState::updateBottomBarVisible,
        )
        loginGraph(
            navigateToSignUp = { kakaoId ->
                appState.navController.navigateToSignUp(
                    kakaoId = kakaoId,
                    navOptions = navOptions {
                        popUpTo<Login> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            navigateToHome = {
                appState.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Login> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            innerPadding = innerPadding,
        )

        signUpGraph(
            navigateToRegion = {
                appState.navController.navigateToRegion(
                    navOptions = navOptions {
                        popUpTo<Login> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    },
                )
            },
            navigateToHome = {
                appState.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Login> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    },
                )
            },
            navigateUp = appState.navController::navigateUp,
            innerPadding = innerPadding,
        )

        noticeGraph(
            navigateUp = appState.navController::navigateUp,
            navigateToMatching = { initialTab ->
                appState.navController.navigateToMatching(
                    initTab = initialTab,
                    navOptions = navOptions {
                        popUpTo<Notice> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            navigateToConfirmReview = { reviewId ->
                appState.navController.navigateToConfirmReview(
                    reviewId = reviewId,
                )
            },
        )

        writeGraph(
            navigateToMatching = { initialTab ->
                appState.navController.navigateToMatching(
                    initTab = initialTab,
                    navOptions = navOptions {
                        popUpTo<Matching> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    },
                )
            },
            navController = appState.navController,
        )

        regionGraph(
            innerPadding = innerPadding,
            navController = appState.navController,
        )

        rankingGraph(
            innerPadding = innerPadding,
            navController = appState.navController,
        )

        tierInfoGraph(
            innerPadding = innerPadding,
            navController = appState.navController,
        )

        addSportsGraph(
            navigateUp = appState.navController::navigateUp,
        )

        confirmReviewGraph(
            navController = appState.navController,
        )
    }
}
