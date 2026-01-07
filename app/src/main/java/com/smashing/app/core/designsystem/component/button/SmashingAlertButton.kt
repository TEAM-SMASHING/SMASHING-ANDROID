package com.smashing.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.extension.noRippleClickable

//TODO 디자인 시스템 정의 후 수정

/**
 *  다이얼로그에서 사용되는 Alert 공용 버튼 컴포넌트입니다.
 *  @param text 버튼 내부에 표시될 텍스트
 * @param onClick 버튼 클릭 시 실행될 콜백 함수
 * @param isPrimary true일 경우 파란색(Primary), false일 경우 회색(Secondary) 배경이 적용
 */
@Composable
fun SmashingAlertButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(40.dp)
            .background(
                color = if (isPrimary) Color(0xFF2261FF) else Color(0xFF363C4B),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .noRippleClickable(onClick = onClick),

        ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}


@Preview
@Composable
private fun SmashingAlertPrimaryMediumButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        SmashingAlertButton(
            text = "text",
            onClick = {},
            isPrimary = true,
            Modifier
                .width(130.dp),
        )
        SmashingAlertButton(
            text = "text",
            onClick = {},
            isPrimary = true,
            Modifier
                .width(273.dp),
        )
        SmashingAlertButton(
            text = "text",
            onClick = {},
            isPrimary = false,
            Modifier
                .width(130.dp),
        )
    }
}
