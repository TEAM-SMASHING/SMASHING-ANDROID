package com.smashing.app.core.extension

import androidx.navigation.navOptions
import com.smashing.app.presentation.home.navigation.HomeUser

fun clearBackStackOptions() = navOptions {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
}

fun tabNavigationOptions() = navOptions {
    popUpTo(HomeUser) {
        saveState = true
        inclusive = false
    }
    launchSingleTop = true
    restoreState = true
}
