package com.smashing.app.presentation.signup.component.chatlink

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.smashing.app.R.string.sign_up_chat_link_title
import com.smashing.app.R.string.sign_up_chat_link_subtitle
import com.smashing.app.R.string.sign_up_chat_link_placeholder
import com.smashing.app.core.designsystem.component.textfield.SmashingInputTextField
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle

@Composable
fun SignUpChatLink(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        SignUpTitle(
            title = stringResource(sign_up_chat_link_title),
            subTitle = stringResource(sign_up_chat_link_subtitle),
        )

        SmashingInputTextField(
            state = rememberTextFieldState(),
            placeholder = stringResource(sign_up_chat_link_placeholder),
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
