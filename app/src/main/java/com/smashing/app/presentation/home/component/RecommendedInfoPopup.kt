package com.smashing.app.presentation.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smashing.app.R.string.home_recommend_info_popup
import com.smashing.app.core.designsystem.theme.SmashingTheme

private val INFO_POPUP_TAIL_TOP_OFFSET = 8.dp
private val INFO_POPUP_BODY_TOP_OFFSET = 14.dp

@Composable
fun RecommendedInfoTooltip(
    isVisible: Boolean,
    anchorHeightPx: Int,
    onDismiss: () -> Unit,
    tailOffsetX: Dp = 0.dp,
) {
    if (!isVisible) return
    val density = LocalDensity.current
    val tailOffsetY = anchorHeightPx + with(density) { INFO_POPUP_TAIL_TOP_OFFSET.roundToPx() }
    val bodyOffsetY = anchorHeightPx + with(density) { INFO_POPUP_BODY_TOP_OFFSET.roundToPx() }

    Popup(
        alignment = Alignment.TopCenter,
        offset = IntOffset(
            x = 0,
            y = tailOffsetY,
        ),
        onDismissRequest = {},
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        RecommendedInfoPopupTail(tailOffsetX = tailOffsetX)
    }

    Popup(
        alignment = Alignment.TopCenter,
        offset = IntOffset(
            x = 0,
            y = bodyOffsetY,
        ),
        onDismissRequest = onDismiss,
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        RecommendedInfoPopupBody()
    }
}
@Composable
private fun RecommendedInfoPopupTail(
    modifier: Modifier = Modifier,
    tailOffsetX: Dp = 0.dp,
) {
    val popupBackgroundColor = SmashingTheme.colors.bgCanvasReverse
    Canvas(
        modifier = modifier
            .offset(x = tailOffsetX)
            .size(width = 12.dp, height = 6.dp),
    ) {
        val triangle = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(0f, size.height)
            lineTo(size.width, size.height)
            close()
        }
        drawPath(
            path = triangle,
            color = popupBackgroundColor,
        )
    }
}

@Composable
private fun RecommendedInfoPopupBody(
    modifier: Modifier = Modifier,
) {
    val popupBackgroundColor = SmashingTheme.colors.bgCanvasReverse
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(4.dp))
            .background(color = popupBackgroundColor)
            .padding(
                vertical = 9.dp,
                horizontal = 12.dp,
            ),
    ) {
        Text(
            text = stringResource(home_recommend_info_popup),
            style = SmashingTheme.typography.xxs.medium10,
            color = SmashingTheme.colors.txtPrimaryReverse,
        )
    }
}
