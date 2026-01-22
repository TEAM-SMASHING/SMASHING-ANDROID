package com.smashing.app.presentation.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.network.sse.SseManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sseManager: SseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sseManager.start()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )

        setContent {
            SmashingAndroidTheme {
                val appState = rememberMainAppState()

                MainScreen(
                    appState = appState,
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sseManager.stop()
    }
}
