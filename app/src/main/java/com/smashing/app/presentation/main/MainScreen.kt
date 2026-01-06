package com.smashing.app.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.smashing.app.presentation.dummy.dummyGraph
import com.smashing.app.presentation.home.navigation.Home
import com.smashing.app.presentation.home.navigation.homeGraph
import com.smashing.app.presentation.login.navigation.Login
import com.smashing.app.presentation.login.navigation.loginGraph
import com.smashing.app.presentation.main.component.MainBottomBar
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
        containerColor = Color.White,
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
        )

        dummyGraph(
            innerPadding = innerPadding,
        )

        loginGraph(
            navigateToHome = {
                appState.navController.navigate(
                    route = Home,
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
    }
}
