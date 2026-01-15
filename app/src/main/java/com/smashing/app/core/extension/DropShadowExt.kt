package com.smashing.app.core.extension

import android.graphics.BlurMaskFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * 드롭 섀도우 효과를 추가하는 Modifier extension 함수입니다.
 * 컴포저블 요소에 그림자 효과를 적용하여 깊이감과 입체감을 제공합니다.
 * 
 * @param shape 그림자의 모양을 결정하는 [Shape]입니다.
 *   예: [RoundedCornerShape], [CircleShape] 등
 * @param color 그림자의 색상입니다. 기본값은 검은색 25% 투명도입니다.
 * @param blur 그림자의 블러 효과 강도입니다. 값이 클수록 더 부드러운 그림자가 됩니다.
 *   기본값은 1.dp입니다.
 * @param offsetY 그림자의 Y축 오프셋입니다. 양수 값은 아래쪽으로, 음수 값은 위쪽으로 이동합니다.
 *   기본값은 1.dp입니다.
 * @param offsetX 그림자의 X축 오프셋입니다. 양수 값은 오른쪽으로, 음수 값은 왼쪽으로 이동합니다.
 *   기본값은 1.dp입니다.
 * @param spread 그림자의 확장 크기입니다. 원본 크기에서 이 값만큼 확장되어 그림자가 그려집니다.
 *   기본값은 1.dp입니다.
 * */
@Composable
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 1.dp,
    offsetY: Dp = 1.dp,
    offsetX: Dp = 1.dp,
    spread: Dp = 1.dp
) = composed {
    val density = LocalDensity.current

    val paint = remember(color, blur) {
        Paint().apply {
            this.color = color
            val blurPx = with(density) { blur.toPx() }
            if (blurPx > 0f) {
                this.asFrameworkPaint().maskFilter =
                    BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
            }
        }
    }

    drawBehind {
        val spreadPx = spread.toPx()
        val offsetXPx = offsetX.toPx()
        val offsetYPx = offsetY.toPx()

        val shadowWidth = size.width + spreadPx
        val shadowHeight = size.height + spreadPx

        if (shadowWidth <= 0f || shadowHeight <= 0f) return@drawBehind

        val shadowSize = Size(shadowWidth, shadowHeight)
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.translate(offsetXPx, offsetYPx)
            canvas.drawOutline(shadowOutline, paint)
            canvas.restore()
        }
    }
}