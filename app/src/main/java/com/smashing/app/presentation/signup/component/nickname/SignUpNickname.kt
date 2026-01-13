package com.smashing.app.presentation.signup.component.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle

@Composable
fun SignUpNickName (
    onDuplicateBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
    ){
        SignUpTitle(
            title = "닉네임을 입력해주세요",
            subTitle = "한글, 영어, 숫자만 가능해요"
        )

        Spacer(modifier = Modifier.height(28.dp))

        Row (
            modifier = modifier,
        ){
            NicknameInputTextField(
                state = rememberTextFieldState(),
                placeholder = "닉네임을 입력해주세요.",
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(10.dp))

            SmashingBaseButton(
                text = "중복확인",
                textStyle = SmashingTheme.typography.sm.medium14,
                onClick = onDuplicateBtnClick,
                buttonColor = ButtonStyle.PRIMARY_WITH_DISABLED.getButtonColor(),
                contentPadding = PaddingValues(
                    vertical = 13.dp,
                    horizontal = 12.dp,
                ),
                shape = RoundedCornerShape(8.dp),
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SignUpNickNamePreview() {
    SmashingAndroidTheme {
        SignUpNickName(
            onDuplicateBtnClick = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
