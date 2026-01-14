package com.smashing.app.core.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.delay

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

/**
 * 포커스된 컴포저블을 키보드에 가리지 않도록 컴포넌트 영역 안으로 이동시키는 함수
 * @param isFocused  bring-into-view 동작 실행 여부를 결정하는 상태 값
 * @param delayMillis  키보드 표시 이후 동작 실행까지 대기할 지연 시간 값(ms)
 */
fun Modifier.bringIntoViewOnFocus(
    isFocused: Boolean,
    delayMillis: Long = 400L,
): Modifier = composed {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var layoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)
    val isImeVisible = imeBottom > 0

    LaunchedEffect(isFocused, isImeVisible) {
        if (!isFocused || !isImeVisible) return@LaunchedEffect
        val coords = layoutCoordinates ?: return@LaunchedEffect

        val rect = Rect(
            left = 0f,
            top = 0f,
            right = coords.size.width.toFloat(),
            bottom = coords.size.height.toFloat(),
        )

        delay(delayMillis)
        bringIntoViewRequester.bringIntoView(rect)
    }

    this
        .bringIntoViewRequester(bringIntoViewRequester)
        .onGloballyPositioned { layoutCoordinates = it }
}
