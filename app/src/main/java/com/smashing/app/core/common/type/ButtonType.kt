package com.smashing.app.core.common.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors

enum class ButtonType {
    PRIMARY,
    PRIMARY_WITH_DISABLED,
    SECONDARY,
    DISABLED_ACTIVE,
    REJECTED,
    DIMMED;

    @Composable
    fun getButtonColor(): SmashingBtnColor {
        return when (this) {
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
                textColor = colors.btnBgPrimaryActive,
            )

            DISABLED_ACTIVE -> SmashingBtnColor(
                backgroundColor = colors.btnBgPrimaryDisabled,
                textColor = colors.btnTxtSecondaryActive,
            )

            REJECTED -> SmashingBtnColor(
                backgroundColor = colors.btnBgPrimaryActive,
                textColor = colors.btnTxtRejected,
            )

            DIMMED -> SmashingBtnColor(
                backgroundColor = colors.btnBgTertiaryPressed,
                textColor = colors.btnTxtPrimaryPressed,
            )
        }
    }
}

data class SmashingBtnColor(
    val backgroundColor: Color,
    val textColor: Color,
    val disabledBackgroundColor: Color = backgroundColor,
    val disabledTextColor: Color = textColor,
)
