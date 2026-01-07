package com.smashing.app.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.smashing.app.core.common.type.DialogType
import com.smashing.app.core.designsystem.component.button.SmashingAlertButton

/**
 * 다이얼로그 공통 컴포넌트입니다.
 * @param title 다이얼로그 중앙 상단에 표시될 메인 제목 텍스트
 * @param onDismissRequest 다이얼로그 외부 클릭 또는 뒤로가기 버튼 클릭 시 호출되는 콜백
 * @param subtitle (Optional) 제목 아래에 표시될 부가 설명 텍스트. 값이 없으면 표시되지 않음.
 */
@Composable
fun SmashingDialog(
    title: String,
    onDismissRequest: () -> Unit,
    type: DialogType,
    confirmText: String,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    dismissText: String? = null,
    onDismissClick: (() -> Unit)? = null,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        SmashingDialogContent(
            title = title,
            type = type,
            confirmText = confirmText,
            onConfirmClick = onConfirmClick,
            modifier = modifier,
            subtitle = subtitle,
            dismissText = dismissText,
            onDismissClick = onDismissClick,
        )
    }
}


@Composable
private fun SmashingDialogContent(
    title: String,
    type: DialogType,
    confirmText: String,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    dismissText: String? = null,
    onDismissClick: (() -> Unit)? = null,
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
        ) {
            when (type) {
                DialogType.ALERT -> {
                    SmashingAlertButton(
                        text = confirmText,
                        onClick = onConfirmClick,
                        isPrimary = true, // 1개일 때는 강조색
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                DialogType.CONFIRM -> {
                    // 왼쪽: 아니요 (보조)
                    SmashingAlertButton(
                        text = dismissText ?: "",
                        onClick = { onDismissClick?.invoke() },
                        isPrimary = false,
                        modifier = Modifier.weight(1f),
                    )
                    // 오른쪽: 예 (주요)
                    SmashingAlertButton(
                        text = confirmText,
                        onClick = onConfirmClick,
                        isPrimary = true,
                        modifier = Modifier.weight(1f),
                    )

                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF1B1E26)
@Composable
private fun SmashingDialogAlertPreview() {
    // 버튼이 1개인 ALERT 타입 (서브타이틀 포함)
    SmashingDialog(
        title = "매칭 상대가 작성 완료한 경기입니다",
        subtitle = "매칭 결과 확인을 통해 확인해주세요",
        type = DialogType.ALERT,
        confirmText = "확인",
        onConfirmClick = {},
        onDismissRequest = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1B1E26)
@Composable
private fun SmashingDialogConfirmPreview() {
    // 버튼이 2개인 CONFIRM 타입 (서브타이틀 포함)
    SmashingDialog(
        title = "매칭 결과를 제출하시겠습니까?",
        subtitle = "정확한 경기결과가 아닐 경우 반려됩니다",
        type = DialogType.CONFIRM,
        confirmText = "예",
        dismissText = "아니요",
        onConfirmClick = {},
        onDismissClick = {},
        onDismissRequest = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1B1E26)
@Composable
private fun SmashingDialogNoSubtitlePreview() {
    SmashingDialog(
        title = "동네를 변경하시겠습니까?",
        type = DialogType.CONFIRM,
        confirmText = "예",
        dismissText = "아니요",
        onConfirmClick = {},
        onDismissClick = {},
        onDismissRequest = {}
    )
}
