package com.smashing.app.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smashing.app.R.drawable.ic_home_selected
import com.smashing.app.R.drawable.ic_home_unselected
import com.smashing.app.R.drawable.ic_profile_selected
import com.smashing.app.R.drawable.ic_profile_unselected
import com.smashing.app.R.drawable.ic_search_selected
import com.smashing.app.R.drawable.ic_search_unselected
import com.smashing.app.R.drawable.ic_trophy_selected
import com.smashing.app.R.drawable.ic_trophy_unselected
import com.smashing.app.R.string.home
import com.smashing.app.R.string.matching_manage
import com.smashing.app.R.string.matching_search
import com.smashing.app.R.string.profile
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
        selectedIconRes = ic_home_selected,
        unselectedIconRes = ic_home_unselected,
        titleRes = home,
        route = HomeUser,
    ),
    SEARCH(
        selectedIconRes = ic_search_selected,
        unselectedIconRes = ic_search_unselected,
        titleRes = matching_search,
        route = SearchMain,
    ),
    MATCHING(
        selectedIconRes = ic_trophy_selected,
        unselectedIconRes = ic_trophy_unselected,
        titleRes = matching_manage,
        route = Matching,
    ),
    PROFILE(
        selectedIconRes = ic_profile_selected,
        unselectedIconRes = ic_profile_unselected,
        titleRes = profile,
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
