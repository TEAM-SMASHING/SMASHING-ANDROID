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
import com.smashing.app.R
import com.smashing.app.core.common.style.BorderInputStyle
import com.smashing.app.core.designsystem.component.textfield.SmashingBasicTextField
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

@Composable
fun NicknameInputTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isConfirm: Boolean = false,
    errorText: String? = null,
    maxLength: Int = 10,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    onKeyboardAction: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val currentLength by remember { derivedStateOf { state.text.length } }
    val isFilled by remember { derivedStateOf { state.text.isNotEmpty() } }
    val inputState = BorderInputStyle.from(
        isFocused = isFocused,
        isFilled = isFilled,
        isError = isError,
        isConfirm = isConfirm,
    )
    Column(modifier = modifier) {
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
                    onKeyboardAction()
                },
                inputTransformation = InputTransformation.maxLength(maxLength),
                suffix = {
                    Text(
                        text = "$currentLength/$maxLength",
                        style = SmashingTheme.typography.xs.regular12,
                        color = inputState.getContentColor(),
                    )
                }
            )
        }
        if (isConfirm) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp, end = 7.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                    contentDescription = "Success",
                    tint = SmashingTheme.colors.iconSuccess,
                )
                Spacer(modifier = Modifier.padding(start = 7.dp))
                Text(
                    text = "사용 가능한 닉네임 입니다.",
                    color = SmashingTheme.colors.txtMuted,
                    style = SmashingTheme.typography.xs.regular12,
                )
            }
        }
        if (isError && !errorText.isNullOrBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_warning),
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
                isError = true,
                errorText = "이미 존재하는 닉네임이에요.",
            )
        }
    }
}
