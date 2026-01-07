package com.smashing.app.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.material3.MaterialTheme

object SmashingTheme {
    val colors: SmashingColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSmashingColors.current

    val typography: SmashingTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalSmashingTypography.current
}

@Composable
fun ProvideSmashingColorsAndTypography(
    colors: SmashingColors,
    typography: SmashingTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSmashingColors provides colors,
        LocalSmashingTypography provides typography,
        content = content,
    )
}

@Composable
fun SmashingAndroidTheme(
    content: @Composable () -> Unit,
) {
    ProvideSmashingColorsAndTypography(
        colors = defaultSmashingColors,
        typography = defaultSmashingTypography,
    ) {
        MaterialTheme(
            content = content,
        )
    }
}