package com.smashing.app.core.designsystem.component.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.smashing.app.R.drawable.ic_close_lg
import com.smashing.app.R.drawable.ic_menu
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 * 기본 탑바 컴포넌트입니다.
 * 화면 상단에 제목과 네비게이션 아이콘을 표시하며, [TopBarType]에 따라 아이콘의 종류와 위치가 결정됩니다.
 *
 * @param title 탑바 중앙에 표시될 제목 텍스트
 * @param topBarType 탑바의 타입으로, 아이콘 표시 방식을 결정합니다.
 *   - [TopBarType.DEFAULT]: 아이콘 없이 제목만 표시
 *   - [TopBarType.BACK]: 왼쪽에 뒤로가기 아이콘 표시 (화면 뒤로 이동)
 *   - [TopBarType.CLOSE]: 오른쪽에 닫기 아이콘 표시 (현재 화면 닫기)
 *   - [TopBarType.BACK_WITH_MENU]: 왼쪽에 뒤로가기, 오른쪽에 메뉴 아이콘 표시
 * @param onClick 아이콘 클릭 시 실행될 콜백 함수입니다.
 *   [TopBarType.BACK] 또는 [TopBarType.CLOSE]일 때 사용하며,
 *   [TopBarType.BACK_WITH_MENU]일 때는 왼쪽 뒤로가기 클릭 시 사용됩니다.
 * @param onMenuClick [TopBarType.BACK_WITH_MENU]일 때 오른쪽 메뉴 아이콘 클릭 시 실행될 콜백입니다.
 * @param modifier 적용할 Modifier
 */
@Composable
fun SmashingDefaultTopBar(
    title: String,
    topBarType: TopBarType,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onMenuClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        if((topBarType == TopBarType.BACK || topBarType == TopBarType.BACK_WITH_MENU) && onClick != null){
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_left),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .noRippleClickable(
                        onClick = onClick,
                    ),
            )
        }

        Text(
            text = title,
            style = SmashingTheme.typography.md.semibold16,
            color = SmashingTheme.colors.txtPrimary,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 21.dp),
        )

        if(topBarType == TopBarType.CLOSE && onClick != null){
            Icon(
                imageVector = ImageVector.vectorResource(ic_close_lg),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .noRippleClickable(
                        onClick = onClick,
                    ),
            )
        }

        if(topBarType == TopBarType.BACK_WITH_MENU && onMenuClick != null){
            Icon(
                imageVector = ImageVector.vectorResource(ic_menu),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .noRippleClickable(
                        onClick = onMenuClick,
                    ),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_Default() {
    SmashingDefaultTopBar(
        title = "제목",
        topBarType = TopBarType.DEFAULT,
        onClick = null,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_Back() {
    SmashingDefaultTopBar(
        title = "뒤로가기",
        topBarType = TopBarType.BACK,
        onClick = {},
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_Close() {
    SmashingDefaultTopBar(
        title = "닫기",
        topBarType = TopBarType.CLOSE,
        onClick = {},
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_BackWithMenu() {
    SmashingDefaultTopBar(
        title = "제목",
        topBarType = TopBarType.BACK_WITH_MENU,
        onClick = {},
        onMenuClick = {},
    )
}
