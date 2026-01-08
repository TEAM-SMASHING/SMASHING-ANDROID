package com.smashing.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
 * 버튼 기본 컴포넌트
 * @param text 버튼 텍스트
 * @param onClick 버튼 클릭 이벤트
 * @buttonColor 버튼 색상
 * @contentPadding 버튼 내부 패딩
 * @isEnabled 버튼 활성화 여부
 */
@Composable
fun SmashingBaseButton(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    buttonColor: SmashingBtnColor,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
) {
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
        Box(
            contentAlignment = Alignment.Center,
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
            )
        }
    }
}
