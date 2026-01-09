package com.smashing.app.presentation.search.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.R.drawable.ic_close_sm
import com.smashing.app.core.designsystem.theme.SmashingTheme

enum class FilterType {
    DEFAULT,
    VARIANT;

    @Composable
    fun getStyle(): FilterStyleData = when (this) {
        DEFAULT -> FilterStyleData(
            bgColor = SmashingTheme.colors.bgSurfacePressed,
            txtColor = SmashingTheme.colors.txtPrimary,
            iconRes = ic_arrow_down,
            iconTint = SmashingTheme.colors.iconPrimary,
        )

        VARIANT -> FilterStyleData(
            bgColor = SmashingTheme.colors.bgSelected,
            txtColor = SmashingTheme.colors.txtPrimaryReverse,
            iconRes = ic_close_sm,
            iconTint = SmashingTheme.colors.iconTertiary,
        )
    }
}

data class FilterStyleData(
    val bgColor: Color,
    val txtColor: Color,
    val iconRes: Int,
    val iconTint: Color,
)
