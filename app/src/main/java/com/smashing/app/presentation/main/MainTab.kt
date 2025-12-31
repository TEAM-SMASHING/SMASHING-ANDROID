package com.smashing.app.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.smashing.app.R.drawable.ic_launcher_background
import com.smashing.app.R.string.home
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.home.navigation.Home

enum class MainTab(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    val route: MainTabRoute,
) {
    HOME(
        iconRes = ic_launcher_background,
        titleRes = home,
        route = Home,
    ),

    // TODO: 추후 변경 예정
    Dummy(
        iconRes = ic_launcher_background,
        titleRes = home,
        route = Home,
    ),
    Dummy1(
        iconRes = ic_launcher_background,
        titleRes = home,
        route = Home,
    ),
    Dummy2(
        iconRes = ic_launcher_background,
        titleRes = home,
        route = Home,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
