package com.smashing.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.common.type.ButtonType
import com.smashing.app.core.common.type.SmashingBtnColor
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

/**
 * 버튼 공통 컴포넌트
 * 버튼 구현시 사용하는 컴포넌트입니다.
 * @param text 버튼 텍스트
 * @param onClick 버튼 클릭 이벤트
 * @param buttonColor 버튼 색상
 * @param contentPadding 버튼 내부 패딩
 * @param isEnabled 버튼 활성화 여부
 * @param isRippleEnabled 리플 효과 여부
 */
@Composable
fun SmashingBaseButton(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    buttonColor: SmashingBtnColor,
    contentPadding: PaddingValues,
    shape: Shape,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isRippleEnabled: Boolean = true,
) {
    val rippleConfig = if (isRippleEnabled) LocalRippleConfiguration.current else null

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfig) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = isEnabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor.backgroundColor,
                contentColor = buttonColor.textColor,
                disabledContainerColor = buttonColor.disabledBackgroundColor,
                disabledContentColor = buttonColor.disabledTextColor,
            ),
            contentPadding = contentPadding,
        ) {
            Text(
                text = text,
                style = textStyle,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmashingBaseButtonPreview() {
    SmashingAndroidTheme {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            SmashingBaseButton(
                text = "중복확인",
                textStyle = SmashingTheme.typography.sm.medium14,
                onClick = {},
                buttonColor = ButtonType.PRIMARY_WITH_DISABLED.getButtonColor(),
                contentPadding = PaddingValues(
                    vertical = 13.dp,
                    horizontal = 12.dp,
                ),
                shape = RoundedCornerShape(8.dp),
            )
        }
    }
}
