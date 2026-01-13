package com.smashing.app.presentation.signup.component.chatlink

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.component.textfield.SmashingInputTextField
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle
import com.smashing.app.presentation.signup.component.nickname.NicknameInputTextField

@Composable
fun SignUpChatLink(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        SignUpTitle(
            title = "카카오톡 오픈채팅 링크를 입력해주세요",
            subTitle = "매칭이 확정되면 상대방이 확인할 수 있어요",
        )

        SmashingInputTextField(
            state = rememberTextFieldState(),
            placeholder = "오픈채팅 링크를 입력해주세요",
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun SignUpNickNamePreview() {
    SmashingAndroidTheme {
        SignUpChatLink(
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
