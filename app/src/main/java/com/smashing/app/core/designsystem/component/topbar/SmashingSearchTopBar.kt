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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.ic_arrow_left
import com.smashing.app.core.designsystem.component.textfield.SearchTextField
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

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