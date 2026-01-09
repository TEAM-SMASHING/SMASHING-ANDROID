package com.smashing.app.core.designsystem.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smashing.app.core.designsystem.theme.SmashingTheme

enum class ChipStyle {
    ACTIVE, INACTIVE, DISABLED, PRESSED;

    @Composable
    fun backgroundColor(): Color = when (this) {
        ACTIVE -> SmashingTheme.colors.bgCanvasReverse
        INACTIVE -> Color.Transparent
        DISABLED -> SmashingTheme.colors.bgOverlay
        PRESSED -> SmashingTheme.colors.btnBgPrimaryPressed
    }

    @Composable
    fun contentColor(): Color = when (this) {
        ACTIVE -> SmashingTheme.colors.txtPrimaryReverse
        INACTIVE -> SmashingTheme.colors.txtSecondary
        DISABLED -> SmashingTheme.colors.txtSecondary
        PRESSED -> SmashingTheme.colors.btnTxtPrimaryPressed
    }

    @Composable
    fun borderColor(): Color = when (this) {
        ACTIVE -> SmashingTheme.colors.bgCanvasReverse
        INACTIVE -> SmashingTheme.colors.borderSecondary
        DISABLED -> Color.Transparent
        PRESSED -> SmashingTheme.colors.bgCanvasReverse
    }
}
