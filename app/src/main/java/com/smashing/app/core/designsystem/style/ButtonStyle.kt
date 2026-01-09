package com.smashing.app.core.designsystem.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors

enum class ButtonStyle {
    PRIMARY,
    PRIMARY_WITH_DISABLED,
    SECONDARY,
    DISABLED_ACTIVE,
    REJECTED,
    TERTIARY;

    @Composable
    fun getButtonColor(): SmashingBtnColor = when (this) {
        PRIMARY -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryActive,
            textColor = colors.btnTxtPrimaryActive,
        )

        PRIMARY_WITH_DISABLED -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryActive,
            textColor = colors.btnTxtPrimaryActive,
            disabledBackgroundColor = colors.btnBgPrimaryDisabled,
            disabledTextColor = colors.btnTxtPrimaryDisabled,
        )

        SECONDARY -> SmashingBtnColor(
            backgroundColor = colors.btnBgSecondaryActive,
            textColor = colors.btnTxtSecondaryActive,
        )

        DISABLED_ACTIVE -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryDisabled,
            textColor = colors.btnTxtSecondaryActive,
        )

        REJECTED -> SmashingBtnColor(
            backgroundColor = colors.btnBgPrimaryActive,
            textColor = colors.btnTxtRejected,
        )

        TERTIARY -> SmashingBtnColor(
            backgroundColor = colors.btnBgTertiaryPressed,
            textColor = colors.btnTxtPrimaryPressed,
        )
    }
}

data class SmashingBtnColor(
    val backgroundColor: Color,
    val textColor: Color,
    val disabledBackgroundColor: Color = backgroundColor,
    val disabledTextColor: Color = textColor,
)
