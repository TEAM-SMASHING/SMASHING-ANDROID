package com.smashing.app.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.home.navigation.homeGraph
import com.smashing.app.presentation.home.navigation.navigateToHome
import com.smashing.app.presentation.login.navigation.Login
import com.smashing.app.presentation.login.navigation.loginGraph
import com.smashing.app.presentation.main.component.MainBottomBar
import com.smashing.app.presentation.matching.navigation.Matching
import com.smashing.app.presentation.matching.navigation.matchingGraph
import com.smashing.app.presentation.matching.navigation.navigateToMatching
import com.smashing.app.presentation.notice.navigation.navigateToNotice
import com.smashing.app.presentation.notice.navigation.noticeGraph
import com.smashing.app.presentation.profile.navigation.profileGraph
import com.smashing.app.presentation.search.navigation.searchGraph
import com.smashing.app.presentation.signup.navigation.navigateToSignUp
import com.smashing.app.presentation.signup.navigation.signUpGraph
import com.smashing.app.presentation.write.navigation.navigateToSubmit
import com.smashing.app.presentation.write.navigation.writeGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    appState: MainAppState,
) {
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()

    Scaffold(
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
            .fillMaxSize(),
    ) { innerPadding ->
        MainNavHost(
            appState = appState,
            innerPadding = innerPadding,
        )
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
            innerPadding = innerPadding,
            navigateToNotice = appState.navController::navigateToNotice,
        )

        searchGraph(
            innerPadding = innerPadding,
        )

        matchingGraph(
            innerPadding = innerPadding,
            navigateToSubmit = appState.navController::navigateToSubmit,
        )

        profileGraph(
            innerPadding = innerPadding,
        )

        loginGraph(
            navigateToSignUp = { authId ->
                appState.navController.navigateToSignUp(
                    authId = authId,
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
            innerPadding = innerPadding,
        )

        noticeGraph(
            navigateUp = appState.navController::navigateUp,
            innerPadding = innerPadding,
        )

        writeGraph(
            navigateToMatching = {
                appState.navController.navigateToMatching(
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
    }
}
