package com.smashing.app.presentation.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.theme.SmashingTheme

@Composable
fun RecommendedInfoPopup(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bubbleColor = SmashingTheme.colors.bgCanvasReverse
    val triangleWidth = 12.dp
    val triangleHeight = 6.dp

    Box(
        modifier = modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(color = bubbleColor)
                .padding(
                    vertical = 9.dp,
                    horizontal = 12.dp,
                ),
        ) {
            Text(
                text = "내 동내에서 LP ± 200점 범위 안의 5명의 유저가 랜덤으로 추천돼요!",
                style = SmashingTheme.typography.xxs.medium10,
                color = SmashingTheme.colors.txtPrimaryReverse,
            )
        }

        Canvas(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 12.dp)
                .height(triangleHeight)
        ) {
            val centerX = size.width / 2
            val trianglePath = Path().apply {
                moveTo(centerX - triangleWidth.toPx() / 2, 0f)
                lineTo(centerX, -triangleHeight.toPx())
                lineTo(centerX + triangleWidth.toPx() / 2, 0f)
                close()
            }
            drawPath(
                path = trianglePath,
                color = bubbleColor
            )
        }
    }
}