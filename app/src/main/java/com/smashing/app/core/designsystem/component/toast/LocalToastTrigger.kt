package com.smashing.app.core.designsystem.component.toast

import androidx.compose.runtime.staticCompositionLocalOf

val LocalToastTrigger = staticCompositionLocalOf<(message: String) -> Unit> {
    error("No Toast provided")
}
