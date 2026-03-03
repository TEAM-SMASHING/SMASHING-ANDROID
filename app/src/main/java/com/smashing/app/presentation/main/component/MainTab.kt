package com.smashing.app.presentation.main.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smashing.app.R
import com.smashing.app.core.common.navigation.MainTabRoute
import com.smashing.app.core.common.navigation.Route
import com.smashing.app.presentation.home.navigation.HomeUser
import com.smashing.app.presentation.matching.navigation.Matching
import com.smashing.app.presentation.profile.navigation.MyProfile
import com.smashing.app.presentation.search.navigation.SearchMain

enum class MainTab(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    @StringRes val titleRes: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIconRes = R.drawable.ic_home_selected,
        unselectedIconRes = R.drawable.ic_home_unselected,
        titleRes = R.string.home,
        route = HomeUser,
    ),
    SEARCH(
        selectedIconRes = R.drawable.ic_search_selected,
        unselectedIconRes = R.drawable.ic_search_unselected,
        titleRes = R.string.matching_search,
        route = SearchMain,
    ),
    MATCHING(
        selectedIconRes = R.drawable.ic_trophy_selected,
        unselectedIconRes = R.drawable.ic_trophy_unselected,
        titleRes = R.string.matching_manage,
        route = Matching,
    ),
    PROFILE(
        selectedIconRes = R.drawable.ic_profile_selected,
        unselectedIconRes = R.drawable.ic_profile_unselected,
        titleRes = R.string.profile,
        route = MyProfile,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return entries.any { predicate(it.route) }
        }
    }
}
