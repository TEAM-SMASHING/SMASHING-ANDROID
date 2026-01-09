package com.smashing.app.presentation.search.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.smashing.app.R.drawable.ic_arrow_down
import com.smashing.app.R.drawable.ic_close_sm
import com.smashing.app.core.designsystem.theme.SmashingTheme

enum class FilterStyle {
    DEFAULT,
    VARIANT;
}

data class FilterStyleData(
    val bgColor: Color,
    val txtColor: Color,
    val iconRes: Int,
    val iconTint: Color,
)
