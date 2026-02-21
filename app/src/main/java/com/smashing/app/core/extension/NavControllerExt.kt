package com.smashing.app.core.extension

import androidx.navigation.NavController
import androidx.navigation.navOptions

fun NavController.clearBackStackOptions() = navOptions {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
}
