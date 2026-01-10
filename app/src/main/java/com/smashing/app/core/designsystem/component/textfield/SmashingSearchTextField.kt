package com.smashing.app.core.designsystem.component.textfield


import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
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
import com.smashing.app.R.drawable.ic_search_sm
import com.smashing.app.core.designsystem.style.ColoredBoxTextFieldStyle
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 *  검색 텍스트 필드 공통 컴포넌트입니다.
 * @param state 텍스트 필드의 상태 (입력값 및 커서 위치 관리)
 * @param onSearch 키보드의 검색(돋보기) 버튼 클릭 시 호출되는 콜백 (입력된 텍스트 반환)
 * @param placeholder 입력값이 없을 때 표시되는 힌트 텍스트
 *
 */

@Composable
fun SearchTextField(
    state: TextFieldState,
    onSearch: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isFilled = state.text.isNotEmpty()
    val focusManager = LocalFocusManager.current
    val inputState = ColoredBoxTextFieldStyle.from(
        isFocused,
        isFilled,
    )
    Row(
        modifier = modifier
            .background(
                color = inputState.getBackgroundColor(),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 13.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isFilled) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_search_sm),
                contentDescription = null,
                tint = SmashingTheme.colors.iconSecondary,
            )
            Spacer(modifier = Modifier.padding(start = 4.dp))
        }

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
            suffix = {
                if (isFocused && isFilled) {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_circle_x),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .noRippleClickable(onClick = state::clearText),
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun SearchTextFieldStatesPreview() {
    SmashingAndroidTheme {
        Box(modifier = Modifier.padding(20.dp))
        {

            SearchTextField(
                state = remember { TextFieldState() },
                placeholder = "닉네임을 입력해주세요",
                modifier = Modifier.fillMaxWidth(),
                onSearch = { query ->
                    // 실제 검색 로직 수행 (예: ViewModel 호출)
                    println("검색어: $query")
                },
            )
        }
    }
}
