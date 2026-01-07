package com.smashing.app.presentation.matchingsearch.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.R.drawable.ic_close_sm
import com.smashing.app.core.designsystem.theme.SmashingTheme

enum class FilterType {
    DEFAULT,
    VARIANT;

    @Composable
    fun getStyle(): FilterStyle = when (this) {
        DEFAULT -> FilterStyle(
            bgColor = SmashingTheme.colors.bgSurfacePressed,
            txtColor = SmashingTheme.colors.txtPrimary,
            iconRes = ic_arrow_down,
            iconTint = SmashingTheme.colors.iconPrimary,
        )

        VARIANT -> FilterStyle(
            bgColor = SmashingTheme.colors.bgSelected,
            txtColor = SmashingTheme.colors.txtPrimaryReverse,
            iconRes = ic_close_sm,
            iconTint = SmashingTheme.colors.iconTertiary,
        )
    }
}

data class FilterStyle(
    val bgColor: Color,
    val txtColor: Color,
    val iconRes: Int,
    val iconTint: Color,
)
