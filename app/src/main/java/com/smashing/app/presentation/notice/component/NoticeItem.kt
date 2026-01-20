package com.smashing.app.presentation.notice.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smashing.app.core.designsystem.component.image.UrlImage
import com.smashing.app.core.designsystem.mapper.icon
import com.smashing.app.core.designsystem.theme.SmashingAndroidTheme
import com.smashing.app.core.designsystem.theme.SmashingTheme
import com.smashing.app.core.extension.noRippleClickable
import com.smashing.app.core.util.ProfileImageProvider
import com.smashing.app.data.type.SportType

@Composable
fun NoticeItem(
    title: String,
    description: String,
    userId: String,
    nickname: String,
    sportType: SportType,
    isRead: Boolean,
    timeAgo: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (!isRead) SmashingTheme.colors.bgSurface
        else SmashingTheme.colors.bgCanvas

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(
                    onClick = onItemClick,
                )
                .background(backgroundColor)
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement
                .spacedBy(
                    space = 12.dp,
                )
        ) {
            UrlImage(
                placeholderDrawable = ProfileImageProvider.getTempImg(nickname),
                modifier = Modifier
                    .height(40.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )

            Column {
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(sportType.icon()),
                        contentDescription = null,
                        tint = SmashingTheme.colors.iconPrimary,
                        modifier = Modifier.padding(end = 2.dp),
                    )

                    Text(
                        text = title,
                        style = SmashingTheme.typography.md.semibold16,
                        color = SmashingTheme.colors.txtPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = timeAgo,
                        style = SmashingTheme.typography.xs.regular12,
                        color = SmashingTheme.colors.txtTertiary,
                    )
                }

                Text(
                    text = description,
                    style = SmashingTheme.typography.sm.medium14,
                    color = SmashingTheme.colors.txtSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = SmashingTheme.colors.borderPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeItemPreview() {
    SmashingAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
        ) {
            NoticeItem(
                title = "매칭 결과가 반려되었어요",
                description = "\"와쿠와쿠\"님이 결과 입력을 거절했습니다. (사유: 점수 오류)",
                userId = "323",
                sportType = SportType.PING_PONG,
                isRead = false,
                timeAgo = "2시간 전",
                onItemClick = {},
                nickname = "와쿠와쿠"
            )

            NoticeItem(
                title = "매칭이 수락 되었어요",
                description = "\"닝우닝\"(Silver)님이 매칭을 수락했어요! 지금 확인 해볼까요?",
                userId = "323",
                sportType = SportType.TENNIS,
                isRead = true,
                timeAgo = "2시간 전",
                onItemClick = {},
                nickname = "닝우닝"
            )
        }
    }
}
