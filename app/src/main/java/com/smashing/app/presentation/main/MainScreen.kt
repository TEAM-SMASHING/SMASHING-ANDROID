package com.smashing.app.presentation.main

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.NavHost
import com.smashing.app.core.designsystem.component.toast.LocalToastTrigger
import com.smashing.app.core.designsystem.component.toast.SmashingToast
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.presentation.addsports.navigation.addSportsGraph
import com.smashing.app.presentation.confirmreview.navigation.confirmReviewGraph
import com.smashing.app.presentation.home.navigation.homeGraph
import com.smashing.app.presentation.login.navigation.loginGraph
import com.smashing.app.presentation.main.component.MainBottomBar
import com.smashing.app.presentation.main.component.MainTab
import com.smashing.app.presentation.main.state.MainAppState
import com.smashing.app.presentation.matching.navigation.matchingGraph
import com.smashing.app.presentation.mypage.navigation.myPageGraph
import com.smashing.app.presentation.notice.navigation.noticeGraph
import com.smashing.app.presentation.profile.navigation.profileGraph
import com.smashing.app.presentation.ranking.navigation.rankingGraph
import com.smashing.app.presentation.region.navigation.regionGraph
import com.smashing.app.presentation.report.navigation.reportGraph
import com.smashing.app.presentation.search.navigation.searchGraph
import com.smashing.app.presentation.signup.navigation.signUpGraph
import com.smashing.app.presentation.tierinfo.navigation.tierInfoGraph
import com.smashing.app.presentation.write.navigation.writeGraph
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

private const val EXIT_MILLIS = 3000L

@Composable
fun MainScreen(
    appState: MainAppState,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarMutex = remember { Mutex() }

    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()
    val onShowToast: (String) -> Unit = remember(coroutineScope, snackBarHostState, snackBarMutex) {
        { message ->
            coroutineScope.launch {
                if (!snackBarMutex.tryLock()) return@launch

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
                    snackBarMutex.unlock()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { effect ->
                when (effect) {
                    is MainContract.SideEffect.ShowToast -> onShowToast(effect.message)
                }
            }
    }

    val activity = LocalActivity.current

    BackHandler(
        enabled = isBottomBarVisible && currentTab != null,
    ) {
        if (currentTab == MainTab.HOME) {
            activity?.finish()
        } else {
            appState.navigate(MainTab.HOME)
        }
    }

    CompositionLocalProvider(
        LocalToastTrigger provides onShowToast,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                bottomBar = {
                    MainBottomBar(
                        isVisible = isBottomBarVisible,
                        tabs = MainTab.entries.toImmutableList(),
                        currentTab = currentTab,
                        onTabSelected = appState::navigate,
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            if (isBottomBarVisible) {
                                bottomBarHeight = with(density) {
                                    coordinates.size.height.toDp()
                                }
                            }
                        },
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
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(
                        alignment = Alignment.BottomCenter,
                    )
                    .padding(
                        bottom = bottomBarHeight + 12.dp
                    )
                    .windowInsetsPadding(WindowInsets.ime),
            ) { data ->
                SmashingToast(
                    text = data.visuals.message,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
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
            innerPadding = innerPadding,
        )

        searchGraph(
            navController = appState.navController,
            innerPadding = innerPadding,
        )

        matchingGraph(
            navController = appState.navController,
            innerPadding = innerPadding,
        )

        profileGraph(
            navController = appState.navController,
            innerPadding = innerPadding,
        )
        myPageGraph(
            navController = appState.navController,
            innerPadding = innerPadding,
        )

        loginGraph(
            navController = appState.navController,
            innerPadding = innerPadding,
        )

        signUpGraph(
            navController = appState.navController,
        )

        noticeGraph(
            navController = appState.navController,
        )

        writeGraph(
            navController = appState.navController,
        )

        regionGraph(
            innerPadding = innerPadding,
            navController = appState.navController,
        )

        rankingGraph(
            navController = appState.navController,
        )

        reportGraph(
            innerPadding = innerPadding,
            navController = appState.navController,
        )

        tierInfoGraph(
            innerPadding = innerPadding,
            navController = appState.navController,
        )

        addSportsGraph(
            navController = appState.navController,
        )

        confirmReviewGraph(
            navController = appState.navController,
        )
    }
}
