package com.smashing.app.core.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.common.style.BorderInputStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

@Composable
fun SmashingAreaTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isConfirm: Boolean = false,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
    onKeyboardAction: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
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
                    shape = RoundedCornerShape(12.dp),
                )
                .height(156.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp),
        ) {
            SmashingBasicTextField(
                state = state,
                modifier = Modifier.weight(1f),
                placeholder = placeholder,
                placeholderColor = inputState.getContentColor(),
                placeholderStyle = inputState.getTextStyle(),
                textColor = inputState.getContentColor(),
                textStyle = inputState.getTextStyle(),
                inputTransformation = inputTransformation,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                lineLimits = TextFieldLineLimits.MultiLine(
                    minHeightInLines = 1,
                    maxHeightInLines = Int.MAX_VALUE
                ),
                onKeyboardAction = {
                    if (keyboardOptions.imeAction == ImeAction.Done) {
                        focusManager.clearFocus()
                        onKeyboardAction()
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun SmashingAreaTextFieldPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SmashingAreaTextField(
                state = rememberTextFieldState(),
                placeholder = "ex) 실력이 안맞았는데도 배려해서 경기해주셨어요.",
            )
            SmashingAreaTextField(
                state = rememberTextFieldState("입력 완료"),
                isConfirm = true,
                placeholder = "입력 완료",
            )
            SmashingAreaTextField(
                state = rememberTextFieldState("글자 수 초과"),
                placeholder = "text",
                isError = true,
            )
        }
    }
}
