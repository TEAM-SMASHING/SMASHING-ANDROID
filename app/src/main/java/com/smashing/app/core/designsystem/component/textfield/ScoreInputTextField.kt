package com.smashing.app.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.style.ColoredBoxTextFieldStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.bringIntoViewOnFocus


private const val SCORE_INPUT_RATIO = 45f / 41f

@Composable
fun ScoreInputTextField(
    state: TextFieldState,
    placeholder: String,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val isFilled = state.text.isNotEmpty()
    val focusManager = LocalFocusManager.current

    val inputState = ColoredBoxTextFieldStyle.from(isFocused, isFilled)

    val digitOnlyFilter = remember {
        InputTransformation {
            val filtered = asCharSequence()
                .filter { it.isDigit() }
                .take(2)
                .toString()

            if (filtered != asCharSequence().toString()) {
                replace(0, length, filtered)
            }
        }
    }

    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)
    val isImeVisible = imeBottom > 0

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus()
            onDoneClick()
        }
    }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            state.edit { placeCursorAtEnd() }
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(SCORE_INPUT_RATIO)
            .background(
                color = inputState.getBackgroundColor(),
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = inputState.getBorderColor(),
                shape = RoundedCornerShape(8.dp),
            )
            .bringIntoViewOnFocus(
                isFocused = isFocused,
                extraBottom = 0.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        SmashingBasicTextField(
            state = state,
            isEnabled = isEnabled,
            contentAlignment = Alignment.Center,
            textColor = inputState.getContentColor(),
            textStyle = inputState.getTextStyle().copy(
                textAlign = TextAlign.Center,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.NumberPassword,
            ),
            onKeyboardAction = {
                focusManager.clearFocus()
            },
            inputTransformation = digitOnlyFilter,
            placeholder = if (isFocused) "" else placeholder,
            placeholderColor = SmashingTheme.colors.txtDisabled,
            placeholderStyle = SmashingTheme.typography.sm.medium14.copy(
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .onFocusEvent { focusState ->
                    isFocused = focusState.isFocused
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScoreInputTextFieldPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ScoreInputTextField(
                    state = rememberTextFieldState(),
                    placeholder = "0",
                )
                ScoreInputTextField(
                    state = rememberTextFieldState(),
                    placeholder = "0",
                )
            }
        }
    }
}
