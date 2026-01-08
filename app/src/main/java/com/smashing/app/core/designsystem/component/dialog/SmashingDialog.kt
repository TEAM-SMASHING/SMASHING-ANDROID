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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smashing.app.core.common.type.DialogType
import com.smashing.app.core.designsystem.component.button.SmashingAlertButton
import com.smashing.app.core.designsystem.theme.SmashingTheme

/**
 * 다이얼로그 공통 컴포넌트입니다.
 * [DialogType]에 따라 알림(Alert) 또는 확인(Confirm) 모드로 동작하며,
 * 이에 따라 버튼의 개수와 배치가 자동으로 변경됩니다.
 *
 * @param title 다이얼로그 상단에 표시될 메인 제목 텍스트
 * @param onDismissRequest 다이얼로그 외부(Scrim) 클릭 또는 뒤로가기 버튼 클릭 시 호출되는 콜백
 * @param type 다이얼로그 타입 (CONFIRM: 확인 버튼 1개, ALERT: 취소/확인 버튼 2개)
 * @param confirmText 확인(Primary) 버튼에 표시될 텍스트 (CONFIRM 모드에서는 유일한 버튼, ALERT 모드에서는 우측 버튼)
 * @param onConfirmClick 확인(Primary) 버튼 클릭 시 실행될 콜백
 * @param subtitle (Optional) 제목 아래에 표시될 부가 설명 텍스트. null일 경우 표시되지 않음.
 * @param dismissText (Optional) 취소(Secondary) 버튼에 표시될 텍스트. [DialogType.ALERT]일 때만 좌측에 표시됨.
 * @param onDismissClick (Optional) 취소(Secondary) 버튼 클릭 시 실행될 콜백. [DialogType.ALERT]일 때만 동작함.
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
                color = SmashingTheme.colors.bgOverlay,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(
            text = title,
            color = SmashingTheme.colors.txtPrimary,
            style = SmashingTheme.typography.md.semibold16,
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                color = SmashingTheme.colors.txtTertiary,
                style = SmashingTheme.typography.sm.regular14,
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
                DialogType.CONFIRM -> {
                    SmashingAlertButton(
                        text = confirmText,
                        onClick = onConfirmClick,
                        isPrimary = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                DialogType.ALERT -> {
                    SmashingAlertButton(
                        text = dismissText ?: "",
                        onClick = { onDismissClick?.invoke() },
                        isPrimary = false,
                        modifier = Modifier.weight(1f),
                    )
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


@Preview
@Composable
private fun SmashingDialogConfirmPreview() {
    // 버튼이 1개인 ALERT 타입
    SmashingDialog(
        title = "매칭 상대가 작성 완료한 경기입니다",
        subtitle = "매칭 결과 확인을 통해 확인해주세요",
        type = DialogType.CONFIRM,
        confirmText = "확인",
        onConfirmClick = {},
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun SmashingDialogAlertPreview() {
    // 버튼이 2개인 CONFIRM 타입
    SmashingDialog(
        title = "매칭 결과를 제출하시겠습니까?",
        subtitle = "정확한 경기결과가 아닐 경우 반려됩니다",
        type = DialogType.ALERT,
        confirmText = "예",
        dismissText = "아니요",
        onConfirmClick = {},
        onDismissClick = {},
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun SmashingDialogNoSubtitlePreview() {
    // 버튼이 2개인 CONFIRM+SubTitle 없는 타입
    SmashingDialog(
        title = "동네를 변경하시겠습니까?",
        type = DialogType.ALERT,
        confirmText = "예",
        dismissText = "아니요",
        onConfirmClick = {},
        onDismissClick = {},
        onDismissRequest = {}
    )
}
