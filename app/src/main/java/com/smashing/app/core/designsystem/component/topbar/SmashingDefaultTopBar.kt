package com.smashing.app.core.designsystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.smashing.app.R.drawable.ic_close_lg
import com.smashing.app.core.designsystem.style.TopBarType
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable

@Composable
fun SmashingDefaultTopBar(
    title: String,
    topBarType: TopBarType,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        if(topBarType == TopBarType.BACK && onClick != null){
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
                        onClick = onClick
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