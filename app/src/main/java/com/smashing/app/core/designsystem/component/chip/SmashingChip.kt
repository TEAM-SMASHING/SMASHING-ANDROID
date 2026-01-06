package com.smashing.app.core.designsystem.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smashing.app.R.drawable.ic_fake_red


//TODO 디자인 시스템 등록 후 수정 현재는 임시로 값 할당함
enum class ChipState(
    val backgroundColor: Color,
    val contentColor: Color,
    val borderColor: Color? = null,
) {
    Active(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        borderColor = Color(0xFFE2E6EA)
    ),
    Inactive(
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        borderColor = Color(0xFF252A36)
    ),
    Disabled(
        backgroundColor = Color(0xFF252A36),
        contentColor = Color.White
    )

}

/**
 *Smashing Chip
 *
 * 클릭 가능한 chip component
 * 아이콘, 텍스트 포함하며 세가지 상태(Active, Inactive, Disabled)를 나타냄
 *
 * @param text Chip에 표시될 텍스트
 * @param state Chip의 상태 (Active, Inactive, Disabled)
 * @param onClick 칩 클릭 시 실행될 콜백 함수
 * @param icon 표시할 아이콘(선택사항)
 *
 */

@Composable
fun SmashingChip(
    text: String,
    state: ChipState,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconContentDescription: String? = null,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        enabled = state != ChipState.Disabled,
        shape = RoundedCornerShape(12.dp),
        color = state.backgroundColor,
        border = state.borderColor?.let { BorderStroke(1.dp, it) },
        contentColor = state.contentColor,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconContentDescription,
                    tint = state.contentColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(
                text = text,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(400),
                    color = state.contentColor
                )
            )

        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFBFC5D0)
@Composable
fun PreviewSmashingChips() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SmashingChip(
            text = "text",
            state = ChipState.Active,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {}
        )

        SmashingChip(
            text = "text",
            state = ChipState.Inactive,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {}
        )

        SmashingChip(
            text = "text",
            state = ChipState.Disabled,
            icon = ImageVector.vectorResource(id = ic_fake_red),
            iconContentDescription = "icon",
            onClick = {}
        )
    }
}
