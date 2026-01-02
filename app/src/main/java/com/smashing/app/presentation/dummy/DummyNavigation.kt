package com.smashing.app.presentation.dummy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.smashing.app.core.common.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToDummy(
    navOptions: NavOptions? = null
) = navigate(Dummy, navOptions)

fun NavGraphBuilder.dummyGraph(
    innerPadding: PaddingValues,
) {
    composable<Dummy> {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Dummy Screen",
            )
        }
    }
}

@Serializable
data object Dummy : MainTabRoute
