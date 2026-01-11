package com.smashing.app.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.style.BorderInputStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.checkLength

private const val AREA_RATIO = 296 / 128f

@Composable
fun SmashingAreaTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    maxLength: Int = 100,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val currentLength = state.text.toString().checkLength()
    val isFilled = state.text.isNotEmpty()
    val lengthLimitTransformation = InputTransformation.maxLength(maxLength)
    val combinedTransformation = inputTransformation?.then(lengthLimitTransformation)
        ?: lengthLimitTransformation
    val inputState = BorderInputStyle.from(
        isFocused = isFocused,
        isFilled = isFilled,
        isError = isError,
        isConfirm = false,
    )
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = inputState.getBorderColor(),
                shape = RoundedCornerShape(12.dp),
            )
            .aspectRatio(AREA_RATIO)
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SmashingBasicTextField(
                state = state,
                placeholder = placeholder,
                placeholderColor = inputState.getContentColor(),
                placeholderStyle = inputState.getTextStyle(),
                textColor = inputState.getContentColor(),
                textStyle = inputState.getTextStyle(),
                interactionSource = interactionSource,
                inputTransformation = combinedTransformation.maxLength(maxLength),
                keyboardOptions = keyboardOptions,
                lineLimits = TextFieldLineLimits.MultiLine(),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onKeyboardAction = { focusManager.clearFocus() },
            )
            Text(
                text = "$currentLength / $maxLength",
                style = SmashingTheme.typography.xs.regular12,
                color = inputState.getContentColor(),
                modifier = Modifier.align(Alignment.End)
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
                .background(color = SmashingTheme.colors.bgSurface)
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
