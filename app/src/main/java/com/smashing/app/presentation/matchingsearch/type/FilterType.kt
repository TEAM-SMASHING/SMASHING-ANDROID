package com.smashing.app.presentation.matchingsearch.type

import androidx.compose.ui.graphics.Color

enum class FilterType {
    DEFAULT, VARIANT
}

data class FilterStyle(
    val bgColor: Color,
    val txtColor: Color,
    val iconRes: Int,
    val iconTint: Color,
)

