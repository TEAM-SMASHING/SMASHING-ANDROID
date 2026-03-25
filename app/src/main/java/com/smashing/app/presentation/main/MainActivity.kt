package com.smashing.app.presentation.main

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.extension.clearBackStackNavOptions
import com.smashing.app.core.network.sse.SseManager
import com.smashing.app.core.network.token.AuthManager
import com.smashing.app.presentation.login.navigation.navigateToLogin
import com.smashing.app.presentation.main.state.rememberMainAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sseManager: SseManager

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )

        setContent {
            SmashingAndroidTheme {
                val appState = rememberMainAppState()

                LaunchedEffect(Unit) {
                    authManager.authEvent.collect {
                        appState.navController.navigateToLogin(
                            appState.navController.clearBackStackNavOptions()
                        )
                    }
                }

                MainScreen(
                    appState = appState,
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sseManager.connect()
    }

    override fun onStop() {
        super.onStop()
        sseManager.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            sseManager.disconnect()
        }
    }
}
