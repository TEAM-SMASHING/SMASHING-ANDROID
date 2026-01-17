package com.smashing.app.presentation.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smashing.app.R.drawable.img_search_result_empty
import com.smashing.app.core.designsystem.theme.SmashingTheme.colors
import com.smashing.app.core.designsystem.theme.SmashingTheme.typography

private const val PADDING_RATIO = 219f/320f

@Composable
fun SearchEmpty(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.weight(PADDING_RATIO))

        Icon(
            painter = painterResource(img_search_result_empty),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "검색 결과가 없습니다",
            color = colors.txtSecondary,
            style = typography.lg.semibold18,
        )
        Text(
            text = "다른 검색어를 입력해보세요",
            color = colors.txtTertiary,
            style = typography.sm.medium14,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
