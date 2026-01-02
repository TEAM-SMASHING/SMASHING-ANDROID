package com.smashing.app.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.smashing.app.core.extension.stateInWhileSubscribed
import com.smashing.app.presentation.dummy.navigateToDummy
import com.smashing.app.presentation.home.navigation.Home
import com.smashing.app.presentation.home.navigation.navigateToHome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@Stable
class MainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val startDestination = Home

    private val currentDestination = navController.currentBackStackEntryFlow
        .map { it.destination }
        .stateInWhileSubscribed(
            scope = coroutineScope,
            initialValue = null,
        )

    val currentTab: StateFlow<MainTab?> = currentDestination
        .map { destination ->
            MainTab.find { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateInWhileSubscribed(
            scope = coroutineScope,
            initialValue = null,
        )

    private val _shouldShowBottomBar = MutableStateFlow(true)

    private val isMainTabRoute: StateFlow<Boolean> = currentDestination
        .map { destination ->
            MainTab.contains { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }.stateInWhileSubscribed(
            scope = coroutineScope,
            initialValue = false,
        )

    val isBottomBarVisible: StateFlow<Boolean> = combine(
        isMainTabRoute,
        _shouldShowBottomBar,
    ) { isMainTab, shouldShow ->
        isMainTab && shouldShow
    }.stateInWhileSubscribed(
        scope = coroutineScope,
        initialValue = false,
    )

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let { route ->
                popUpTo(route) {
                    saveState = true
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        // TODO: navigate 함수 추가 예정
        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions = navOptions)
            MainTab.DUMMY -> navController.navigateToDummy(navOptions = navOptions)
            MainTab.DUMMY1 -> {}
            MainTab.DUMMY2 -> {}
        }
    }

    fun updateBottomBarVisible(isVisible: Boolean) {
        _shouldShowBottomBar.value = isVisible
    }
}

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MainAppState = remember(navController, coroutineScope) {
    MainAppState(navController, coroutineScope)
}
