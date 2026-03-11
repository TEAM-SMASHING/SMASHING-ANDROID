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
import com.smashing.app.core.designsystem.state.TopBarState
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

/**
 * 기본 탑바 컴포넌트입니다.
 * 화면 상단에 제목과 네비게이션 아이콘을 표시하며, [TopBarState]에 따라 아이콘의 종류와 위치가 결정됩니다.
 *
 * @param state 탑바 상태 ([TopBarState.Default], [TopBarState.Back], [TopBarState.Close], [TopBarState.BackWithMenu])
 * @param modifier 적용할 Modifier
 */
@Composable
fun SmashingDefaultTopBar(
    state: TopBarState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (state is TopBarState.Back) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_left),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .noRippleClickable(onClick = state.onBackClick),
            )
        } else if (state is TopBarState.BackWithMenu) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_left),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .noRippleClickable(onClick = state.onBackClick),
            )
        }

        Text(
            text = state.title,
            style = SmashingTheme.typography.md.semibold16,
            color = SmashingTheme.colors.txtPrimary,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 21.dp),
        )

        if (state is TopBarState.Close) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_close_lg),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .noRippleClickable(onClick = state.onCloseClick),
            )
        } else if (state is TopBarState.BackWithMenu) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_menu),
                contentDescription = null,
                tint = SmashingTheme.colors.iconPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .noRippleClickable(onClick = state.onMenuClick),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_Default() {
    SmashingDefaultTopBar(state = TopBarState.Default(title = "제목"))
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_Back() {
    SmashingDefaultTopBar(state = TopBarState.Back(title = "뒤로가기", onBackClick = {}))
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_Close() {
    SmashingDefaultTopBar(state = TopBarState.Close(title = "닫기", onCloseClick = {}))
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SmashingDefaultTopBarPreview_BackWithMenu() {
    SmashingDefaultTopBar(
        state = TopBarState.BackWithMenu(
            title = "제목",
            onBackClick = {},
            onMenuClick = {},
        ),
    )
}
