package com.smashing.app.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smashing.app.R.drawable.ic_fake_red
import com.smashing.app.core.common.type.ChipType
import com.smashing.app.core.extension.noRippleClickable


/**
 *Smashing Chip
 *
 * 클릭 가능한 chip component
 * 아이콘, 텍스트 포함하며 세가지 상태(Active, Inactive, Disabled)를 나타냄
 *
 * @param text Chip에 표시될 텍스트
 * @param state Chip의 상태 (Active, Inactive, Disabled)
 * @param icon 표시할 아이콘(선택사항)
 * @param onClick 칩 클릭 시 실행될 콜백 함수
 *
 *
 */

@Composable
fun SmashingChip(
    text: String,
    state: ChipType,
    onClick: () -> Unit,
    icon: ImageVector,
    iconContentDescription: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(state.backgroundColor)
            .border(
                width = 1.dp,
                color = state.borderColor ?: Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (state != ChipType.DISABLED) {
                    Modifier.noRippleClickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconContentDescription,
            tint = state.contentColor,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight(400),
                color = state.contentColor,
            )
        )

    }
}


@Preview(showBackground = true, backgroundColor = 0xFFBFC5D0)
@Composable
private fun PreviewSmashingChips() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SmashingChip(
            text = "text",
            state = ChipType.ACTIVE,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {},
        )

        SmashingChip(
            text = "text",
            state = ChipType.INACTIVE,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {},
        )

        SmashingChip(
            text = "text",
            state = ChipType.DISABLED,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {},
        )

        SmashingChip(
            text = "text",
            state = ChipType.PRESSED,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {},
        )
    }
}
