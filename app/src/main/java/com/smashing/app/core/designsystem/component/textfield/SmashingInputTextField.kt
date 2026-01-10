package com.smashing.app.core.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_circle_x
import com.smashing.app.R.drawable.ic_warning
import com.smashing.app.core.common.style.BorderInputStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun SmashingInputTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    isConfirm: Boolean = false,
    errorText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    onKeyboardAction: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val isFilled = state.text.isNotEmpty()
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
                .padding(horizontal = 10.dp, vertical = 16.dp),
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
                suffix = {
                    if (isFocused && isFilled) {
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_circle_x),
                            contentDescription = "삭제",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .noRippleClickable(onClick = state::clearText),
                        )
                    }
                }
            )
        }

        if (isError && !errorText.isNullOrBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_warning),
                    contentDescription = "Error",
                    tint = SmashingTheme.colors.iconError,
                )
                Spacer(modifier = Modifier.width(4.dp))
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
private fun SmashingInputTextFieldPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SmashingInputTextField(
                state = rememberTextFieldState(),
                placeholder = "주소를 입력해주세요",
            )

            SmashingInputTextField(
                state = rememberTextFieldState("text"),
                placeholder = "text",
                isError = true,
                errorText = "error message",
            )
        }
    }
}
