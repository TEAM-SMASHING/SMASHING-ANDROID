package com.smashing.app.presentation.chatting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.smashing.app.core.extension.clearBackStackWithRestoreNavOptions
import kotlinx.serialization.Serializable

fun NavController.navigateToChatting(
    navOptions: NavOptions? = clearBackStackWithRestoreNavOptions()
) = navigate(Chatting, navOptions)

@Serializable
data object Chatting
