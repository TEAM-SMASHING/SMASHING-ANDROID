package com.smashing.app.presentation.signup.component.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.component.button.SmashingBaseButton
import com.smashing.app.core.designsystem.style.ButtonStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.presentation.signup.component.SignUpTitle
import com.smashing.app.R.string.sign_up_nickname_title
import com.smashing.app.R.string.sign_up_nickname_subtitle
import com.smashing.app.R.string.sign_up_nickname_duplicate


@Composable
fun SignUpNickName (
    nickNameState: TextFieldState,
    onDuplicateBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier,
    ){
        SignUpTitle(
            title = stringResource(sign_up_nickname_title),
            subTitle = stringResource(sign_up_nickname_subtitle),
        )

        Row (
            modifier = Modifier,
        ){
            NicknameInputTextField(
                state = nickNameState,
                placeholder = stringResource(sign_up_nickname_title),
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(10.dp))

            SmashingBaseButton(
                text = stringResource(sign_up_nickname_duplicate),
                textStyle = SmashingTheme.typography.sm.medium14,
                onClick = onDuplicateBtnClick,
                buttonColor = ButtonStyle.PRIMARY_WITH_DISABLED.getButtonColor(),
                contentPadding = PaddingValues(
                    vertical = 13.dp,
                    horizontal = 12.dp,
                ),
                shape = RoundedCornerShape(8.dp),
                isEnabled = true,
                isRippleEnabled = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpNickNamePreview() {
    SmashingAndroidTheme {
        SignUpNickName(
            nickNameState = rememberTextFieldState(""),
            onDuplicateBtnClick = {},
            modifier = Modifier.background(color = colors.bgCanvas),
        )
    }
}
