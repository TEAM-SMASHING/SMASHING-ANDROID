package com.smashing.app.core.designsystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_left
import com.smashing.app.core.designsystem.component.textfield.SearchTextField
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 * 검색 기능이 있는 탑바 컴포넌트입니다.
 * 화면 상단에 뒤로가기 아이콘과 검색 입력 필드를 표시하며, 검색어 입력 및 뒤로가기 동작을 지원합니다.
 *
 * @param searchState 검색 입력 필드의 상태를 관리하는 [TextFieldState]입니다.
 *   [rememberTextFieldState]를 사용하여 생성하고, 입력된 텍스트는 이 상태를 통해 관리됩니다.
 * @param placeholder 검색 입력 필드에 표시될 플레이스홀더 텍스트입니다.
 * @param onBackClick 뒤로가기 아이콘 클릭 시 실행될 콜백 함수입니다.
 * @param modifier 적용할 Modifier
 */
@Composable
fun SmashingSearchTopBar(
    searchState: TextFieldState,
    placeholder: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_arrow_left),
            contentDescription = null,
            tint = SmashingTheme.colors.iconPrimary,
            modifier = Modifier
                .noRippleClickable(
                    onClick = onBackClick,
                ),
        )

        Spacer(modifier = Modifier.width(8.dp))

        SearchTextField(
            state = searchState,
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingSearchTobBarPreview() {
    SmashingAndroidTheme {
        val searchState = rememberTextFieldState()

        Column(
            modifier = Modifier
                .background(
                    color = SmashingTheme.colors.bgDimmed
                ),
        ) {
            SmashingSearchTopBar(
                searchState = searchState,
                placeholder = "닉네임을 입력해주세요",
                onBackClick = {},
            )

            // 입력된 텍스트 표시 (프리뷰 확인용)
            if (searchState.text.isNotEmpty()) {
                Text(
                    text = "입력된 검색어: ${searchState.text}",
                    color = SmashingTheme.colors.txtPrimary,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
