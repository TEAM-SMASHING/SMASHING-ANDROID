package com.smashing.app.core.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * 리플 효과 없이 클릭 가능하게 만드는 Modifier
 *
 * Material3의 기본 리플 애니메이션을 제거합니다.
 *
 * @param onClick 클릭 시 실행될 콜백
 */
fun Modifier.noRippleClickable(
    onClick: () -> Unit,
    isEnabled: Boolean = true,
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
        enabled = isEnabled,
    )
}
