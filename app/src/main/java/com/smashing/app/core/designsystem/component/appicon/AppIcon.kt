package com.smashing.app.core.designsystem.component.appicon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.R
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme

/**
 * 앱 아이콘을 표시하는 Composable입니다.
 *
 * 앱 아이콘 이미지와 선택적으로 타이틀, 부제목을 함께 표시할 수 있습니다.
 * 주로 스플래시 화면이나 로딩 화면에서 사용됩니다.
 *
 * @param modifier Composable에 적용할 Modifier
 * @param title 아이콘 아래에 표시할 타이틀 텍스트 (선택적)
 * @param subtitle 타이틀 아래에 표시할 부제목 텍스트 (선택적)
 * @param iconSize 아이콘의 크기 (기본값: 100dp)
 * @param isFilled 아이콘의 색상이 채워진 상태인지 여부 (기본값: false)
 */
@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    iconSize: Int = 100,
    isFilled: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(
                if (isFilled) R.drawable.img_app_icon_filled else R.drawable.img_app_icon,
            ),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (title != null) {
            Text(
                text = title,
                style = SmashingTheme.typography.lg.semibold18,
                color = SmashingTheme.colors.txtSecondary,
            )
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = SmashingTheme.typography.sm.medium14,
                color = SmashingTheme.colors.txtTertiary,
            )
        }
    }
}

@Preview
@Composable
private fun AppIconWithTitleAndSubtitlePreview() {
    SmashingAndroidTheme {
        AppIcon(
            title = "앱 이름",
            subtitle = "부제목",
        )
    }
}

@Preview
@Composable
private fun AppIconWithTitleAndSubtitleFilledPreview() {
    SmashingAndroidTheme {
        AppIcon(
            title = "앱 이름",
            subtitle = "부제목",
            isFilled = true,
        )
    }
}