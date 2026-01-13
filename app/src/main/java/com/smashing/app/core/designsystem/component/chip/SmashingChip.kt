package com.smashing.app.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.core.designsystem.style.ChipStyle
import com.smashing.app.core.designsystem.style.ChipStyle.ACTIVE
import com.smashing.app.core.designsystem.style.ChipStyle.DISABLED
import com.smashing.app.core.designsystem.style.ChipStyle.INACTIVE
import com.smashing.app.core.designsystem.style.ChipStyle.PRESSED
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable


/**
 *클릭 가능한 chip component 입니다
 * 아이콘, 텍스트 포함하며 세가지 상태(Active, Inactive, Disabled)를 나타냄
 *
 * @param text Chip에 표시될 텍스트
 * @param style Chip의 상태 스타일 (Active, Inactive, Disabled)
 * @param icon 표시할 아이콘(선택사항)
 * @param onClick 칩 클릭 시 실행될 콜백 함수
 *
 */

@Composable
fun SmashingChip(
    text: String,
    style: ChipStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(style.backgroundColor())
            .border(
                width = 1.dp,
                color = style.borderColor(),
                shape = RoundedCornerShape(999.dp),
            )
            .noRippleClickable(onClick = onClick, isEnabled = style != DISABLED)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = style.contentColor(),
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        Text(
            text = text,
            style = SmashingTheme.typography.sm.medium14,
            color = style.contentColor(),
        )
    }
}

@ReadOnlyComposable
@Composable
private fun ChipStyle.backgroundColor(): Color = when (this) {
    ACTIVE -> SmashingTheme.colors.bgCanvasReverse
    INACTIVE -> Color.Transparent
    DISABLED -> SmashingTheme.colors.bgOverlay
    PRESSED -> SmashingTheme.colors.btnBgPrimaryPressed
}

@ReadOnlyComposable
@Composable
private fun ChipStyle.contentColor(): Color = when (this) {
    ACTIVE -> SmashingTheme.colors.txtPrimaryReverse
    INACTIVE -> SmashingTheme.colors.txtSecondary
    DISABLED -> SmashingTheme.colors.txtSecondary
    PRESSED -> SmashingTheme.colors.btnTxtPrimaryPressed
}

@ReadOnlyComposable
@Composable
fun ChipStyle.borderColor(): Color = when (this) {
    ACTIVE -> SmashingTheme.colors.bgCanvasReverse
    INACTIVE -> SmashingTheme.colors.borderSecondary
    DISABLED -> Color.Transparent
    PRESSED -> SmashingTheme.colors.bgCanvasReverse
}


@Preview
@Composable
private fun PreviewSmashingChips() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        SmashingChip(
            text = "text",
            style = ACTIVE,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            onClick = {},
        )

        SmashingChip(
            text = "text",
            style = INACTIVE,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            onClick = {},
        )

        SmashingChip(
            text = "text",
            style = DISABLED,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            onClick = {},
        )

        SmashingChip(
            text = "text",
            style = PRESSED,
            onClick = {},
        )
    }
}
