package com.smashing.app.presentation.search.style

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

enum class FilterStyle {
    DEFAULT,
    VARIANT;
}

@Immutable
data class FilterStyleData(
    val bgColor: Color,
    val txtColor: Color,
    @DrawableRes val iconRes: Int,
    val iconTint: Color,
)
