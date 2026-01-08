package com.smashing.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.common.type.ButtonType
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

/**
 * 앱의 기본 버튼 컴포넌트
 * Large, Medium, Small 세 가지 크기를 지원
 *
 * @param buttonType 버튼 크기 타입
 * @param text 버튼 텍스트
 * @param onClick 클릭 시 실행될 콜백
 * @param isEnabled 활성화 상태
 */

@Composable
fun SmashingButton(
    buttonType: ButtonType,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {

    SmashingBaseButton(
        text = text,
        textStyle = SmashingTheme.typography.lg.semibold18,
        onClick = onClick,
        buttonColor = buttonType.getButtonColor(),
        contentPadding = PaddingValues(
            vertical = 10.dp,
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        isEnabled = isEnabled,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSmashingButton() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SmashingButton(
                buttonType = ButtonType.PRIMARY_WITH_DISABLED,
                text = "버튼",
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )

            SmashingButton(
                buttonType = ButtonType.PRIMARY_WITH_DISABLED,
                text = "버튼",
                onClick = {},
                isEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )

            SmashingButton(
                buttonType = ButtonType.PRIMARY,
                text = "버튼",
                modifier = Modifier
                    .width(185.dp),
                onClick = {},
            )

            SmashingButton(
                buttonType = ButtonType.DISABLED_ACTIVE,
                text = "버튼",
                modifier = Modifier
                    .width(131.dp),
                onClick = {},
            )
        }
    }
}
