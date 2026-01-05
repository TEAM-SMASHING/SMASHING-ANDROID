package com.smashing.app.core.designsystem.component.textfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme

/**
 *Basic Text Field
 * 텍스트 입력을 위한 기본 텍스트 필드 컴포넌트입니다.
 * 장식이 없는 순수 입력 영역을 제공하며, TextField UI 컴포넌트에서 입력 영역으로 함께 사용됩니다.
 * @param state 텍스트 필드의 텍스트, 커서, 선택 상태를 관리하는 객체
 * @param textColor 입력된 텍스트의 색상
 * @param textStyle 입력된 텍스트의 서체 스타일
 * @param placeholder 입력값이 비어있을 때 보여줄 안내 문구
 * @param placeholderColor 힌트 텍스트 색상
 * @param placeholderStyle 힌트 텍스트 스타일
 * @param isEnabled 활성화 여부 (false일 경우 입력 불가 및 시각적 비활성화)
 * @param isReadOnly 읽기 전용 여부 (입력은 불가하나 텍스트 선택 및 복사는 가능)
 * @param lineLimits 줄 수 제한 (SingleLine 또는 MultiLine 설정)
 * @param cursorColor 텍스트 커서의 색상
 * @param interactionSource 컴포넌트의 상호작용 상태(Focus, Press 등)를 수집하는 통로
 * @param suffix 텍스트 필드 우측 끝에 배치될 추가 요소 (아이콘, 버튼 등)
 *
 */
//todo 디자인시스템 등록 이후 구글 기본 컬러로 되어있는 부분 변경
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SmashingBasicTextField(
    state: TextFieldState,
    textColor: Color,
    textStyle: TextStyle,
    placeholder: String,
    placeholderColor: Color,
    placeholderStyle: TextStyle,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    suffix: (@Composable (() -> Unit))? = null,
) {
    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = isEnabled,
        readOnly = isReadOnly,
        textStyle = textStyle.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(cursorColor),
        lineLimits = lineLimits,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.TopStart,
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = placeholderStyle,
                            color = placeholderColor,
                        )
                    }
                    innerTextField()
                }
                suffix?.invoke()
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun SmashingBasicTextFieldInputPreview() {
    SmashingAndroidTheme {
        val state = rememberTextFieldState(initialText = "http://open.kakao.com/")
        Box(
            modifier = Modifier
                .padding(20.dp)
        ) {
            SmashingBasicTextField(
                state = state,
                placeholder = "placeholder",
                placeholderColor = Color.Gray,
                placeholderStyle = TextStyle(fontSize = 16.sp),
                textColor = Color.Gray,
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }
    }
}
