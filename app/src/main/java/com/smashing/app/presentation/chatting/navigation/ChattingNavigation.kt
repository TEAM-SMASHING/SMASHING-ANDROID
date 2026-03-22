package com.smashing.app.presentation.chatting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.extension.clearBackStackWithRestoreNavOptions
import kotlinx.serialization.Serializable

fun NavController.navigateToChatting(
    navOptions: NavOptions? = clearBackStackWithRestoreNavOptions()
) = navigate(Chatting, navOptions)


fun NavGraphBuilder.chattingGraph(
    navController: NavController,
    innerPadding: PaddingValues,
) {
    composable<Chatting> {
    }
}

@Serializable
data object Chatting : MainTabRoute
