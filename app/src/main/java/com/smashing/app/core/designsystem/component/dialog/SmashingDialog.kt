package com.smashing.app.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smashing.app.core.designsystem.style.DialogStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 * 다이얼로그 공통 컴포넌트입니다.
 * [DialogStyle]에 따라 알림(Alert), 확인(Confirm), 차단/삭제 등 위험 액션(Destructive) 모드로 동작하며,
 * 버튼 개수·배치·확인 버튼 스타일이 자동으로 변경됩니다.
 *
 * - [DialogStyle.CONFIRM]: 확인 버튼 1개
 * - [DialogStyle.ALERT]: 취소/확인 버튼 2개 (일반 스타일)
 * - [DialogStyle.DESTRUCTIVE]: 취소/확인 버튼 2개, 확인 버튼만 경고(빨간) 스타일 (차단·삭제 등)
 *
 * @param title 다이얼로그 상단에 표시될 메인 제목 텍스트
 * @param onDismissClick 다이얼로그가 닫힐 때 호출되는 콜백 (취소 버튼 클릭, 스크림 클릭, 뒤로가기 포함)
 * @param type 다이얼로그 타입 (CONFIRM / ALERT / DESTRUCTIVE)
 * @param confirmText 확인(Primary) 버튼에 표시될 텍스트
 * @param onConfirmClick 확인(Primary) 버튼 클릭 시 실행될 콜백
 * @param subtitle (Optional) 제목 아래에 표시될 부가 설명 텍스트. null일 경우 표시되지 않음.
 * @param dismissText (Optional) 취소(Secondary) 버튼에 표시될 텍스트. [DialogStyle.ALERT], [DialogStyle.DESTRUCTIVE]일 때 좌측에 표시됨.
 */

@Composable
fun SmashingDialog(
    title: String,
    onDismissClick: () -> Unit,
    type: DialogStyle,
    confirmText: String,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    dismissText: String? = null,
) {
    Dialog(onDismissRequest = onDismissClick) {
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
    type: DialogStyle,
    confirmText: String,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    dismissText: String? = null,
    onDismissClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SmashingTheme.colors.bgOverlay,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp,
            ),
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
                DialogStyle.CONFIRM -> {
                    SmashingAlertButton(
                        text = confirmText,
                        onClick = onConfirmClick,
                        containerColor = SmashingTheme.colors.btnBgSecondaryActive,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                DialogStyle.ALERT -> {
                    SmashingAlertButton(
                        text = dismissText ?: "",
                        onClick = onDismissClick,
                        containerColor = SmashingTheme.colors.btnBgTertiaryActive,
                        modifier = Modifier.weight(1f),
                    )
                    SmashingAlertButton(
                        text = confirmText,
                        onClick = onConfirmClick,
                        containerColor = SmashingTheme.colors.btnBgSecondaryActive,
                        modifier = Modifier.weight(1f),
                    )
                }

                DialogStyle.DESTRUCTIVE -> {
                    SmashingAlertButton(
                        text = dismissText ?: "",
                        onClick = onDismissClick,
                        containerColor = SmashingTheme.colors.btnBgTertiaryActive,
                        modifier = Modifier.weight(1f),
                    )
                    SmashingAlertButton(
                        text = confirmText,
                        onClick = onConfirmClick,
                        containerColor = SmashingTheme.colors.btnBgWarning,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}


/**
 *  다이얼로그에서 사용되는 Alert  버튼 컴포넌트입니다.
 *  @param text 버튼 내부에 표시될 텍스트
 * @param onClick 버튼 클릭 시 실행될 콜백 함수
 * @param containerColor 버튼의 배경색
 */

@Composable
private fun SmashingAlertButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(40.dp)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(10.dp),
            )
            .noRippleClickable(onClick = onClick),
    ) {
        Text(
            text = text,
            color = SmashingTheme.colors.txtPrimary,
            style = SmashingTheme.typography.sm.medium14,
        )
    }
}



@Preview
@Composable
private fun SmashingDialogAlertPreview() {
    SmashingAndroidTheme {
        SmashingDialog(
            title = "매칭 결과를 제출하시겠습니까?",
            subtitle = "정확한 경기결과가 아닐 경우 반려됩니다",
            type = DialogStyle.ALERT,
            confirmText = "예",
            dismissText = "아니요",
            onConfirmClick = {},
            onDismissClick = {},
        )
    }
}

@Preview
@Composable
private fun SmashingDialogNoSubtitlePreview() {
    SmashingAndroidTheme {
        SmashingDialog(
            title = "동네를 변경하시겠습니까?",
            type = DialogStyle.ALERT,
            confirmText = "예",
            dismissText = "아니요",
            onConfirmClick = {},
            onDismissClick = {},
        )
    }
}

@Preview
@Composable
private fun SmashingDialogDestructivePreview() {
    SmashingAndroidTheme {
        SmashingDialog(
            title = "이 사용자를 차단하시겠습니까?",
            subtitle = "차단하면 해당 사용자와의 매칭이 제한됩니다.",
            type = DialogStyle.DESTRUCTIVE,
            confirmText = "차단하기",
            dismissText = "취소",
            onConfirmClick = {},
            onDismissClick = {},
        )
    }
}
