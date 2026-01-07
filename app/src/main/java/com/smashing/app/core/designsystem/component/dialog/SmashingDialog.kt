package com.smashing.app.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smashing.app.core.designsystem.component.button.SmashingAlertButton

//TODO 디자인시스템 등록 후 변경
/**
 * 다이얼로그 공통 컴포넌트입니다.
 * @param title 다이얼로그 중앙 상단에 표시될 메인 제목 텍스트
 * @param onDismissRequest 다이얼로그 외부 클릭 또는 뒤로가기 버튼 클릭 시 호출되는 콜백
 * @param buttonContent 다이얼로그 하단에 위치할 버튼 영역. [RowScope]를 제공하여 하나 이상의 [SmashingAlertButton] 등을 가로로 배치.
 * @param subtitle (Optional) 제목 아래에 표시될 부가 설명 텍스트. 값이 없으면 표시되지 않음.
 */
@Composable
fun SmashingDialog(
    title: String,
    onDismissRequest: () -> Unit,
    buttonContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        SmashingDialogContent(
            title = title,
            buttonContent = buttonContent,
            modifier = modifier,
            subtitle = subtitle,
        )
    }
}


@Composable
private fun SmashingDialogContent(
    title: String,
    buttonContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF252A36),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
            content = buttonContent,
        )
    }
}

@Preview
@Composable
private fun SmashingDialogPrimaryPreview() {
    SmashingDialog(
        title = "title",
        subtitle = "subtitle",
        onDismissRequest = {},
        buttonContent = {
            SmashingAlertButton(
                text = "text",
                onClick = {},
                isPrimary = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    )
}

@Preview
@Composable
private fun SmashingDialogPrimaryCompactPreview() {
    SmashingDialog(
        title = "title",
        onDismissRequest = {},
        buttonContent = {
            SmashingAlertButton(
                text = "text",
                onClick = {},
                isPrimary = false,
                modifier = Modifier.weight(1f),
            )
            SmashingAlertButton(
                text = "text",
                onClick = {},
                isPrimary = true,
                modifier = Modifier.weight(1f),
            )
        }
    )
}

@Preview
@Composable
private fun SmashingDialogSecondaryPreview() {
    SmashingDialog(
        title = "title",
        subtitle = "subtitle",
        onDismissRequest = {},
        buttonContent = {
            SmashingAlertButton(
                text = "text",
                onClick = {},
                isPrimary = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    )
}

