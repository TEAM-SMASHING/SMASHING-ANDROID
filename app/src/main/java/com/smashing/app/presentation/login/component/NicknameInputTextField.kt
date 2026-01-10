package com.smashing.app.presentation.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_check
import com.smashing.app.R.drawable.ic_warning
import com.smashing.app.core.common.style.BorderInputStyle
import com.smashing.app.core.designsystem.component.textfield.SmashingBasicTextField
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

@Composable
fun NicknameInputTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    confirmText: String? = null,
    maxLength: Int = 10,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val currentLength by remember { derivedStateOf { state.text.length } }
    val isFilled = state.text.isNotEmpty()
    val isError = !errorText.isNullOrEmpty()
    val isConfirm = !confirmText.isNullOrEmpty()
    val inputState = BorderInputStyle.from(
        isFocused = isFocused,
        isFilled = isFilled,
        isError = isError,
        isConfirm = isConfirm,
    )
    Column(modifier = modifier)
    {
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = inputState.getBorderColor(),
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(horizontal = 13.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmashingBasicTextField(
                state = state,
                modifier = Modifier.weight(1f),
                placeholder = placeholder,
                placeholderColor = inputState.getContentColor(),
                placeholderStyle = inputState.getTextStyle(),
                textColor = inputState.getContentColor(),
                textStyle = inputState.getTextStyle(),
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                onKeyboardAction = {
                    focusManager.clearFocus()
                },
                inputTransformation = InputTransformation.maxLength(maxLength),
                suffix = {
                    Text(
                        text = "$currentLength / $maxLength",
                        style = SmashingTheme.typography.xs.regular12,
                        color = inputState.getContentColor(),
                    )
                }
            )
        }
        if (isError) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_warning),
                    contentDescription = "Error",
                    tint = SmashingTheme.colors.iconError,
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Text(
                    text = errorText,
                    color = SmashingTheme.colors.txtRed,
                    style = SmashingTheme.typography.xs.regular12,
                )
            }
        } else if (isConfirm) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp, end = 7.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_check),
                    contentDescription = "Success",
                    tint = SmashingTheme.colors.iconSuccess,
                )
                Spacer(modifier = Modifier.padding(start = 7.dp))
                Text(
                    text = confirmText,
                    color = SmashingTheme.colors.txtMuted,
                    style = SmashingTheme.typography.xs.regular12,
                )
            }
        }
    }
}

@Preview
@Composable
private fun NicknameInputTextFieldPreview(

) {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .background(color = SmashingTheme.colors.bgSurface)
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NicknameInputTextField(
                state = rememberTextFieldState(),
                placeholder = "닉네임을 입력해주세요.",
            )

            NicknameInputTextField(
                state = rememberTextFieldState("text"),
                placeholder = "text",
                errorText = "이미 존재하는 닉네임이에요.",
            )
            NicknameInputTextField(
                state = rememberTextFieldState("text"),
                placeholder = "text",
                confirmText = "사용 가능한 닉네임 입니다.",
            )
        }
    }
}
