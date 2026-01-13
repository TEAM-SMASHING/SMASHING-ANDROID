package com.smashing.app.core.extension

import androidx.compose.foundation.text.input.TextFieldState

/**
 * TextFieldState의 text를 Int 변환
 */
val TextFieldState.intValue: Int
    get() = text.toString().toIntOrNull() ?: 0
